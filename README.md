# 🍌 Banana Tap Game

Android tapping game using fictional in-game coins.

## Build the APK without Android Studio

1. Upload the **contents of this folder** to a GitHub repository.
2. Open the repository's **Actions** tab.
3. Select **Build Banana Tap APK**.
4. Tap **Run workflow**.
5. Wait for the build to finish.
6. Open the completed workflow run.
7. Under **Artifacts**, download `BananaTap-debug-apk`.
8. Extract it and install `app-debug.apk` on your Android phone.

The workflow builds the APK on GitHub's cloud runner. No Android Studio is required.

## Project structure

The repository must keep these folders:
- `app/`
- `app/src/main/`
- `.github/workflows/`

Do not upload only the individual Java/XML files into the repository root.

## Economy

The current version uses fictional coins only. It does not process UPI, bank transfers, or real-money player-to-player payments.
