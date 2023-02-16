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

- The app uses the adaptative icon with material you enabled and day night theme, so the app will look different on different devices.
- The list of libraries can be found in the [`gradle/libs.versions.toml`](gradle/libs.versions.toml) file.
- The app contains translations for English and Portuguese, including Hour formats, Date formats, and Weekday names.
- Shortcuts to quickly create an appointment, see the appointments, or edit locations available in the launcher.
- Location edition is available in the app.

## Improvements

- [ ] Locations are being soft deleted to avoid inconsistency when reading the appointments; an improvement would be to delete it forever as soon as no appointments are using it anymore.
- [ ] The uses cases are accessing the database directly; an improvement would be to create a repository layer to abstract the database access, including an in-memory LRU cache.
- [ ] Placeholders are pure text, and a good improvement is adding illustrations to them.
- [ ] Date and time are using the device time zone; an improvement would be to allow custom time zones.
- [ ] Integrate the appointments with the alarm manager.
- [X] Save the last used location and use it as a default when creating a new appointment.
- [ ] Add a splash screen.
- [ ] Add a widget to show the next appointment.
- [X] Add the option to share the logs.
- [ ] Show past appointments differently.


## Screenshots

|||||
|---|---|---|---|
|[Home day](assets/screenshots/home-day.png)|[Home night](assets/screenshots/home-night.png)|[Home placeholder day](assets/screenshots/home-empty-day.png)|[Home placeholder night](assets/screenshots/home-empty-night.png)|
|[Edit appointment day](assets/screenshots/edit-appointment-day.png)|[Edit appointment night](assets/screenshots/edit-appointment-night.png)|[New appointment day](assets/screenshots/new-appointment-day.png)|[New appointment night](assets/screenshots/new-appointment-night.png)|
|[Locations day](assets/screenshots/locations-day.png)|[Locations night](assets/screenshots/locations-night.png)|[Add locations day](assets/screenshots/add-location-day.png)|[Add locations night](assets/screenshots/add-location-night.png)|
|[Internationalization English](assets/screenshots/internationalization-english.png)|[Internationalization Portuguese](assets/screenshots/internationalization-portuguese.png)|[Icon and shortcuts](assets/screenshots/icon-and-shortcuts.png)|[Icon and shortcuts material you](assets/screenshots/icon-and-shortcuts-material-you.png)|

![App Icon](assets/ic_launcher-playstore.png)
