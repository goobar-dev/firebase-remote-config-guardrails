# Promoting Firebase Remote Config Parameters from One Project to Another
This sample demonstrates how to transfer ("promote") Firebase Remote Config parameter values from one Firebase project to another.

## Requirements
- Two, or more, Firebase projects
- `GOOGLE_APPLICATION_CREDENTIALS` env variable set to the location of a GCP service account file with Remote Config Admin role for all projects you wish to interact with

## Building the CLI Tool
- Checkout the project
- Run the `installDist` Gradle task
- Add the generated executable to your path

## Using the CLI Tool
Run `remote-config --help`

This should display the help menu and provide details on how to use the cli tool.
```
Usage: remote-config [OPTIONS] REMOTECONFIGPARAMETERS...

Options:
  --from [goobar-training-dev]  Remote Config project to take parameters from
  --to [goobar-training]        Remote Config project to send parameters to
  -h, --help                    Show this message and exit

Arguments:
  REMOTECONFIGPARAMETERS  Remote Config parameters to promote
```

Example usages:
- `remote-config --from=goobar-training-dev --to=goobar-training param1 param2`
- `remote-config param1 param2`