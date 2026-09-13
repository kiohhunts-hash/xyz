# Build the APK on GitHub — no Android Studio required

1. Create a new GitHub repository, for example `filime-assistant`.
2. Upload **all files and folders inside this ZIP** to the repository root.
   Make sure `.github/workflows/build-apk.yml` is present.
3. Commit to the `main` branch.
4. Open the repository's **Actions** tab.
5. Select **Build Android APK**.
6. If it has not already run, click **Run workflow**.
7. Open the successful workflow run.
8. At the bottom, under **Artifacts**, download `Filime-Assistant-debug`.
9. Extract it to get `app-debug.apk`.

Then transfer the APK to Kali and install it with:

    adb install -r app-debug.apk

Launch it with:

    adb shell monkey -p xyz.filime.assistant 1

This workflow uses GitHub-hosted runners, so Kali does not need Android Studio or Gradle installed.
It uses JDK 17 and Gradle 8.2, compatible with the project's Android Gradle Plugin 8.2.
