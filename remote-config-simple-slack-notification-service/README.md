# Monitoring Changes In Firebase Remote Config Using Kotlin, Slack, and Google Cloud Functions
This sample demonstrates how to monitor changes in Firebase Remote Config using a Google Cloud Function.
The Cloud Function is written in Kotlin to send change notifications to Slack.

This function provides only a very simple Slack notification indicating what the new template version is, and who made the change.

## Requirements
- `gcloud` command line tools installed, and authenticated on your development machine
- Cloud Functions enabled in target GCP project
- Artifact Registry enabled in target GCP project
- A Docker image repository setup in Artifact Registry
- A Slack app configured to allow incoming messages via webhook
- A Slack channel id indicating a Slack channel authorized to receive messages from the Slack app

## Deploy from local development machine
```
gcloud functions deploy remote-config-simple-slack-notification-service \
--entry-point RemoteConfigSimpleSlackNotificationService \
--region <target gcp region> \
--docker-repository projects/<project id>/locations/<region>/repositories/<artifact registry repository name> \
--runtime java11 \
--trigger-event google.firebase.remoteconfig.update \
--set-env-vars GOOGLE_CLOUD_PROJECT=<target gcp proejct>,SLACK_TOKEN=<Slack Bot Token>,SLACK_CHANNEL_ID=<Id for target Slack channel>
```