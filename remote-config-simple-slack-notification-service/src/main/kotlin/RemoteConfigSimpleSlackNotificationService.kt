import com.google.cloud.functions.Context
import com.google.cloud.functions.RawBackgroundFunction
import com.google.gson.Gson
import java.util.logging.Logger

/**
 * Entry point for Google Cloud Function that notifies Slack
 * about changes to Firebase Remote Config values.
 *
 * This Cloud Function only notifies slack with the updated Remote Config
 * Template version and who made the change.
 */
class RemoteConfigSimpleSlackNotificationService : RawBackgroundFunction {

    private val slackChannelId: String = System.getenv("SLACK_CHANNEL_ID")
    private val slackToken: String = System.getenv("SLACK_TOKEN")

    private val slack = SlackClient(token = slackToken, channelId = slackChannelId)
    private val logger: Logger = Logger.getLogger(RemoteConfigSimpleSlackNotificationService::class.java.name)

    override fun accept(json: String, context: Context) {

        val event: RemoteConfigUpdateEvent = Gson().fromJson(json, RemoteConfigUpdateEvent::class.java)
        logger.info(event.toString())

        val response = slack.notify("Remote Config changed to version ${event.version} by ${event.user.email}")

        when (response.isOk) {
            true -> logger.info("Slack channel $slackChannelId notified")
            false -> logger.info("Failed to notify Slack channel $slackChannelId: ${response.error}")
        }
    }
}