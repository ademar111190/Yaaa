Yaaa (Yeap Another Appointment App)
===============================

## Yaaa is a simple appointment app that allows you:

- Create appointments with a date, time, location, and description.
- See a list of appointments
- Edit the appointments
- Cancel an appointment

## How to run

### Using Android Studio:

- Clone the project
- Open the project in Android Studio
- Run the app

### Using the command line:

- Clone the project
- Open a terminal in the project folder
- Run `./gradlew assembleDebug`
- Install `adb install app/build/outputs/apk/debug/app-debug.apk`

## How to test

- Open a terminal in the project folder
- Run `./gradlew test`

## Some notes

- The app uses the adaptative icon with material you enabled, so it will look different in different devices.
- The list of libraries can be found in the [`gradle/libs.versions.toml`](gradle/libs.versions.toml) file.
