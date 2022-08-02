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
 * Entry point for Google Cloud Function that notifies Slack
 * about changes to Firebase Remote Config values.
 *
 */
class RemoteConfigAutomatedRollbackService : RawBackgroundFunction {

    private val slackChannelId: String = System.getenv("SLACK_CHANNEL_ID")
    private val slackToken: String = System.getenv("SLACK_TOKEN")

    private val slack = SlackClient(token = slackToken, channelId = slackChannelId)
    private val logger: Logger = Logger.getLogger(RemoteConfigAutomatedRollbackService::class.java.name)

    init { FirebaseApp.initializeApp() }

    override fun accept(json: String, context: Context): Unit = runBlocking {

        val event: RemoteConfigUpdateEvent = Gson().fromJson(json, RemoteConfigUpdateEvent::class.java)

        val remoteConfig = FirebaseRemoteConfig.getInstance()
        logger.info(event.toString())

        val updatedValues = remoteConfig.getTemplateAtVersion(event.version)

        val changesAreValid = updatedValues.parameters.map { (key, parameter) ->
            val validator = ValidatedParameters.getOrDefault(key, { true })
            validator(parameter)
        }.reduceRight { b, acc -> b && acc }

        if (!changesAreValid) {
            remoteConfig.rollback(event.version - 1)
            slack.notify("Invalid Remote Config changes were published.  The changes have been rolled back.")
        } else {
            slack.notify("All Remote Config changes were valid and published")
        }
    }
}