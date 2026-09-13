# Filime Assistant — Android 12 starter

Target: Android 12 / API 31, application ID `xyz.filime.assistant`.

Current features:
- Voice recognition through Android SpeechRecognizer intent
- Flashlight control
- Open Android Settings
- Open YouTube
- Boot receiver scaffold
- Runtime microphone permission

This is intentionally a normal, visible Android application. It does not
attempt to bypass Android security, hide itself, record users covertly, or
survive a factory reset.

## Build
Open this folder in Android Studio. Let Gradle sync, then Build > Build APK(s).

The APK will normally be under:
`app/build/outputs/apk/debug/app-debug.apk`

## Install with ADB
`adb install -r app-debug.apk`

Then launch:
`adb shell monkey -p xyz.filime.assistant 1`
