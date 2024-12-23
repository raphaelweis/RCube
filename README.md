# RCube

RCube is a Rubik's Cube timer app in development. When in 1.0, the app should allow it's users to:

- time themselves when solving a rubik's cube
- store their times in their android phone's local sqlite databases
- find and perform CRUD operations on their previous times
- get interesting statistics for their times such as averages, best, worst, etc...
- get official WCA scrambles for the 3x3 cube using the
  official [TNoodle WCA program](https://www.worldcubeassociation.org/regulations/scrambles/)

RCube is a native android application written in [Kotlin](https://kotlinlang.org/). It targets the
latest Android API, which,
at the time of writing this is [API 35](https://developer.android.com/tools/releases/platforms#15).
The application also uses the Jetpack Compose framework for
it's UI, and the [Room approach](https://developer.android.com/training/data-storage/room) to manage
it's local data. It it compliant with
the [GDPR guidelines](https://gdpr-info.eu/).