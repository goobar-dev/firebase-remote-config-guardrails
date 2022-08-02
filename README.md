# firebase-remote-config-guardrails
A set of utilities for managing Firebase Remote Config as projects scale.

# 🛠 Simple Noficiations for Firebase Remote Config Changes
[`remote-config-simple-slack-notification-service`](https://github.com/goobar-dev/firebase-remote-config-guardrails/tree/main/remote-config-simple-slack-notification-service)

A Google Cloud Function that will notify Slack when Firebase Remote Config values are changed.
The Cloud Function, and resulting Slack message, are very simple; providing just the absolute most basic details about the change event.

ex:
```
Remote Config changed to version 5 by n8ebel@goobar.dev
```

# 🛠 Formatted Notifications for Firebase Remote Config Diffs
[`remote-config-slack-notification-service`](https://github.com/goobar-dev/firebase-remote-config-guardrails/tree/main/remote-config-slack-notification-service)

A Google Cloud Function that will notify Slack when Firebase Remote Config values are changed.
This Cloud Function, and resulting Slack message, are much more detailed.  The function loads the new, and previous, Remote Config values then performs a diff between them to calculate
- which `Parameters` and `Conditions` have been added
- which `Parameters` and `Conditions` have been updated
- which `Parameters` and `Conditions` have been removed

ex:
```
1 changes made to Remote Config in goobar-training
Updated by: <service account name>

:github-check-mark: Updated Parameters:
  • blurry_image_threshold
      • old: {"defaultValue":{~~"value":"false"~~},"conditionalValues":{},~~"valueType":"BOOLEAN"~~}
      • new: {"defaultValue":{**"value":"2.5"**},"conditionalValues":{},**"valueType":"NUMBER"**}
```

* Note, in Slack, this message's markdown formatting is rendered in an easier-to-digest manner.

# 🛠 Automated Rollback of Invalid Firebase Remote Config Changes
[`remote-config-automated-rollback`](https://github.com/goobar-dev/firebase-remote-config-guardrails/tree/main/remote-config-automated-rollback)

A Google Cloud Function that will respond to changes in Firebase Remote Config values by validating changes against a deployed set of validation logic.
If the updated values include any invalid changes, the Remote Config `Template` will be rollect back automatically, and Slack will be notified.

ex:
```
Invalid Remote Config changes were published.  The changes have been rolled back.
```

# 🛠 Promote Firebase Remote Config Parameters to New Firebase Projects
[`remote-config-parameter-promotion-cli`](https://github.com/goobar-dev/firebase-remote-config-guardrails/tree/main/remote-config-promotion-cli)

A JVM-based cli tool that will transfer Firebase Remote Config values from one Firebase project to another.

ex:
```
remote-config --from=goobar-training-dev --to=goobar-training param1 param2
```
