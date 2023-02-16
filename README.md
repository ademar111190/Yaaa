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

- The app uses the adaptative icon with material you enabled and day nigth theme, so it will look different in different devices.
- The list of libraries can be found in the [`gradle/libs.versions.toml`](gradle/libs.versions.toml) file.
- The app contains translations for English and Portuguese.

## Improvements

- [ ] Locations are being soft deleted to avoid inconsistency when reading the appointments; an improvement would be to delete it forever as soon as no appointments are using it anymore.
- [ ] The uses cases are accessing the database directly, an improvement would be to create a repository layer to abstract the database access including an in memory LRU cache.
- [ ] Placeholders are pure text and a good improvement is to add illustrations to them.
- [ ] Date and time are using the device time zone, an improvement would be to allow custom time zones.
- [ ] Integrate the appointments with the alarm manager.
- [ ] Save the last used location and use it as a default when creating a new appointment.
- [ ] Add a splash screen.
- [ ] Add a widget to show the next appointment.
- [ ] Add the option to share the logs.
- [ ] Add Shortcuts to create appointments.
