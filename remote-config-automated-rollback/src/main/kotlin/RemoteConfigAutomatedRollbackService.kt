import com.google.cloud.functions.Context
import com.google.cloud.functions.RawBackgroundFunction
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.logging.Logger

/**
 * Entry point for Google Cloud Function that runs validation against
 * updated Remote Config values and rolls back any invalid changes.
 */
class RemoteConfigAutomatedRollbackService : RawBackgroundFunction {

    private val slackChannelId: String = System.getenv("SLACK_CHANNEL_ID")
    private val slackToken: String = System.getenv("SLACK_TOKEN")

    private val slack = SlackClient(token = slackToken, channelId = slackChannelId)
    private val logger: Logger = Logger.getLogger(RemoteConfigAutomatedRollbackService::class.java.name)

    init { FirebaseApp.initializeApp() }

    override fun accept(json: String, context: Context): Unit = runBlocking {

        // Parse the remoteconfig.update event data
        // and initialize Firebase
        val event: RemoteConfigUpdateEvent = Gson().fromJson(json, RemoteConfigUpdateEvent::class.java)
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        logger.info(event.toString())

        // Load the current version of Remote Config values
        val updatedValues = remoteConfig.getTemplateAtVersion(event.version)

        // Calculate a "valid" / "not valid" state for the updated set of
        // Remote Config values
        val changesAreValid = updatedValues.parameters.map { (key, parameter) ->
            ValidatedParameters.getOrDefault(key, { true })(parameter)
        }.reduceRight { b, acc -> b && acc }

        // Notify Slack if anything was rolled back
        if (!changesAreValid) {
            remoteConfig.rollback(event.version - 1)
            slack.notify("Invalid Remote Config changes were published.  The changes have been rolled back.")
        } else {
            slack.notify("All Remote Config changes were valid and published")
        }
    }
}