# HealthMate Android — Phase 1 starter

An Android Studio project based on the attached HealthMate scope PDF. Built with Kotlin + Jetpack Compose (Android app shell; the PDF's production plan specifies Flutter + Laravel for Android and iOS).

## Included in this starter
- Welcome, sign-in, registration, OTP placeholder and profile setup flows
- Dashboard and navigation for health issues, tracking, allergies, documents, meditation, QR, subscriptions, donor registration and donor search
- Blood-group selection, location/radius form placeholders
- Health-data disclaimer and permission-conscious scope boundaries
- Android project configuration: min API 24, target/compile API 35

## Important: not a production-connected APK
This workspace has no Android SDK/build tools, Laravel API credentials/endpoints, OTP provider, Maps key, subscription catalog, confirmed data fields, or QR contract. Therefore no installable APK has been compiled, and actions marked as placeholders do not persist or transmit health information. The account and OTP screens are UI prototypes, not secure authentication. Do not enter real personal health information.

A production build needs, at minimum:
1. Laravel HTTPS API base URL and endpoint/auth contract.
2. OTP provider and verification/rate-limit rules.
3. Confirmed profile/health measurement fields and consent text.
4. Secure document storage rules and permitted file types.
5. Maps API key, search radius and donor visibility/contact rules.
6. Subscription plans and Google Play Billing configuration.
7. QR format/action, notification/reminder policy, brand assets and app signing credentials.

## Build / download from GitHub using a phone (no laptop needed)
A GitHub Actions workflow is included at `.github/workflows/android-apk.yml`. It builds the debug APK on GitHub and publishes it as a downloadable workflow artifact.

1. Download this ZIP and extract it on your phone (Files app / file manager).
2. In a mobile browser, create a new GitHub repository.
3. Open the new repository and use **Add file → Upload files**. Upload the extracted project contents, including the hidden `.github/workflows/android-apk.yml` file. If the mobile page does not let you select folders or hidden files, use GitHub's browser desktop mode or upload the project ZIP only after extracting; the workflow file must be present in the repository path `.github/workflows/android-apk.yml`.
4. Commit the files to the `main` branch. GitHub Actions will automatically build the APK. If needed, open **Actions → Build HealthMate Android APK → Run workflow**.
5. Open the completed workflow run, scroll to **Artifacts**, and download `healthmate-debug-apk`. Extract it; the file inside is `app-debug.apk`. Android may ask you to allow installation from that browser/file manager. Only install APKs from repositories you trust.

This produces a debug APK suitable for testing, not a Play Store release (which needs a signed release build).

This is a functional UI starter, not the complete HealthMate Phase 1 system or medical product. It does not diagnose, prescribe or replace professional advice/emergency care.
