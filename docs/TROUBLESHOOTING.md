# Troubleshooting Guide

This guide helps you resolve common issues when running the Vehicle Sharing App project.

## Build Issues

### Issue: Could not resolve com.android.tools.build:gradle:X.XX.X

**Symptom:** Build fails with error message about not being able to resolve the Android Gradle Plugin.

**Cause:** 
- Invalid Android Gradle Plugin version in `build.gradle`
- Network connectivity issues preventing dependency downloads
- Incorrect Gradle version

**Solution:**
1. Verify correct versions in `build.gradle`:
   ```gradle
   classpath 'com.android.tools.build:gradle:8.1.0'
   ```

2. Verify correct Gradle version in `gradle/wrapper/gradle-wrapper.properties`:
   ```properties
   distributionUrl=https\://services.gradle.org/distributions/gradle-8.2.1-bin.zip
   ```

3. Check network connectivity:
   ```bash
   ping dl.google.com
   ```

4. Clean and rebuild:
   ```bash
   ./gradlew clean
   ./gradlew build
   ```

---

### Issue: Build fails with "module() API" error

**Symptom:** Error mentioning `DependencyHandler.module()` or deprecated API.

**Cause:** Using incompatible Gradle and Android Gradle Plugin versions.

**Solution:** Use the Gradle wrapper with compatible versions:
- Gradle 8.2.1
- Android Gradle Plugin 8.1.0

See `docs/GRADLE_FIX.md` for detailed information.

---

### Issue: Gradle daemon issues

**Symptom:** Build hangs, crashes, or behaves inconsistently.

**Solution:**
1. Stop all Gradle daemons:
   ```bash
   ./gradlew --stop
   ```

2. Clear Gradle cache:
   ```bash
   rm -rf ~/.gradle/caches/
   rm -rf ~/.gradle/daemon/
   ```

3. Rebuild:
   ```bash
   ./gradlew clean build
   ```

---

## Firebase Issues

### Issue: Firebase authentication fails

**Symptom:** Users cannot sign up or log in; Firebase authentication errors in logs.

**Cause:** 
- Incorrect `google-services.json` configuration
- Package name mismatch between app and Firebase project

**Solution:**
1. Verify package name in `app/build.gradle`:
   ```gradle
   applicationId "com.serenentu.vehiclesharing"
   ```

2. Check `app/google-services.json` has matching package name:
   ```json
   "package_name": "com.serenentu.vehiclesharing"
   ```

3. If package names don't match:
   - Go to [Firebase Console](https://console.firebase.google.com/)
   - Add the correct package name to your Firebase project
   - Download the updated `google-services.json`
   - Replace the file in your project

4. Enable authentication methods in Firebase Console:
   - Go to Authentication → Sign-in method
   - Enable Email/Password authentication
   - (Optional) Enable Google Sign-In

---

### Issue: Firestore operations fail

**Symptom:** Data not saving to or loading from Firestore; permission errors in logs.

**Cause:** 
- Firestore not enabled in Firebase project
- Incorrect security rules
- Network connectivity issues

**Solution:**
1. Enable Firestore in Firebase Console:
   - Go to Firestore Database
   - Click "Create database"
   - Start in test mode for development

2. Verify security rules:
   
   **⚠️ IMPORTANT: These rules are for development/testing only!**
   
   For development:
   ```javascript
   rules_version = '2';
   service cloud.firestore {
     match /databases/{database}/documents {
       match /{document=**} {
         allow read, write: if request.auth != null;
       }
     }
   }
   ```
   
   For production, use more restrictive rules that validate specific fields and conditions. Example:
   ```javascript
   rules_version = '2';
   service cloud.firestore {
     match /databases/{database}/documents {
       match /users/{userId} {
         allow read: if request.auth != null;
         allow write: if request.auth != null && request.auth.uid == userId;
       }
       match /trips/{tripId} {
         allow read: if request.auth != null;
         allow create: if request.auth != null && request.auth.uid == request.resource.data.driverId;
         allow update, delete: if request.auth != null && request.auth.uid == resource.data.driverId;
       }
     }
   }
   ```
   ```

3. Check internet permissions in `AndroidManifest.xml`:
   ```xml
   <uses-permission android:name="android.permission.INTERNET" />
   ```

---

## Android Studio Issues

### Issue: Android Studio shows red errors but build succeeds

**Symptom:** Code editor shows errors but Gradle build works fine.

**Solution:**
1. Sync Gradle files:
   - File → Sync Project with Gradle Files

2. Invalidate caches:
   - File → Invalidate Caches / Restart
   - Select "Invalidate and Restart"

3. Reimport project:
   - Close Android Studio
   - Delete `.idea` folder and `*.iml` files
   - Reopen project in Android Studio

---

### Issue: Layout files not found

**Symptom:** `R.layout.xxx` or `R.id.xxx` cannot be resolved.

**Solution:**
1. Clean and rebuild:
   ```bash
   ./gradlew clean
   ./gradlew build
   ```

2. Sync Gradle:
   - File → Sync Project with Gradle Files

3. Check namespace in `app/build.gradle`:
   ```gradle
   namespace 'com.serenentu.vehiclesharing'
   ```

---

## Runtime Issues

### Issue: App crashes on startup

**Symptom:** App installs but crashes immediately when opened.

**Solution:**
1. Check logcat for errors:
   - View → Tool Windows → Logcat
   - Look for stack traces and error messages

2. Common causes:
   - **Firebase not initialized**: Ensure `google-services.json` is present
   - **Permission issues**: Check `AndroidManifest.xml` has required permissions
   - **Navigation graph issues**: Verify all fragments in `nav_graph.xml`

3. Verify MainActivity is properly configured:
   ```xml
   <activity 
       android:name=".MainActivity"
       android:exported="true">
       <intent-filter>
           <action android:name="android.intent.action.MAIN" />
           <category android:name="android.intent.category.LAUNCHER" />
       </intent-filter>
   </activity>
   ```

---

### Issue: Navigation not working

**Symptom:** App starts but navigation between screens fails; bottom navigation not visible.

**Solution:**
1. Check `nav_graph.xml` has all fragments:
   ```xml
   <fragment android:id="@+id/welcomeFragment" ... />
   <fragment android:id="@+id/loginFragment" ... />
   <fragment android:id="@+id/signupFragment" ... />
   <fragment android:id="@+id/profileFragment" ... />
   <fragment android:id="@+id/browseTripsFragment" ... />
   <fragment android:id="@+id/postTripFragment" ... />
   <fragment android:id="@+id/historyFragment" ... />
   ```

2. Verify bottom navigation menu IDs match navigation graph IDs

3. Check NavHostFragment in `activity_main.xml`:
   ```xml
   <androidx.fragment.app.FragmentContainerView
       android:id="@+id/nav_host_fragment"
       android:name="androidx.navigation.fragment.NavHostFragment"
       app:navGraph="@navigation/nav_graph" />
   ```

---

## Dependency Issues

### Issue: Dependency resolution fails

**Symptom:** Build fails with "Could not resolve" errors for dependencies.

**Solution:**
1. Check internet connectivity

2. Verify repositories in `settings.gradle`:
   ```gradle
   repositories {
       google()
       mavenCentral()
   }
   ```

3. Try rebuilding first:
   ```bash
   ./gradlew clean build
   ```

4. If step 3 doesn't work, clear dependency cache:
   ```bash
   ./gradlew clean --refresh-dependencies
   ```
   **Note:** The `--refresh-dependencies` flag forces re-download of all dependencies, which can be slow. Only use this if a regular clean build doesn't resolve the issue.

5. If behind a proxy, configure in `gradle.properties`:
   ```properties
   systemProp.http.proxyHost=your.proxy.host
   systemProp.http.proxyPort=8080
   systemProp.https.proxyHost=your.proxy.host
   systemProp.https.proxyPort=8080
   ```

---

## Development Environment Issues

### Issue: JDK version problems

**Symptom:** Build fails with Java version errors.

**Solution:**
1. Verify JDK 8 or higher is installed:
   ```bash
   java -version
   ```

2. Check Android Studio JDK settings:
   - File → Project Structure → SDK Location
   - Ensure JDK location is correct

3. Verify in `app/build.gradle`:
   ```gradle
   compileOptions {
       sourceCompatibility JavaVersion.VERSION_1_8
       targetCompatibility JavaVersion.VERSION_1_8
   }
   kotlinOptions {
       jvmTarget = '1.8'
   }
   ```

---

### Issue: Android SDK not found

**Symptom:** Build fails with SDK not found errors.

**Solution:**
1. Install Android SDK 34:
   - Tools → SDK Manager
   - SDK Platforms tab → Check Android 14.0 (API 34)
   - Apply changes

2. Set SDK location:
   - File → Project Structure → SDK Location
   - Set Android SDK location

3. Create `local.properties` if missing:
   ```properties
   sdk.dir=/path/to/Android/sdk
   ```

---

## Still Having Issues?

If you've tried the solutions above and still face issues:

1. **Check the documentation:**
   - `README.md` - Setup instructions
   - `docs/firebase_setup.md` - Firebase configuration
   - `docs/GRADLE_FIX.md` - Gradle version details

2. **Verify your environment:**
   ```bash
   ./gradlew --version
   java -version
   ```

3. **Get detailed logs:**
   ```bash
   ./gradlew build --stacktrace --info
   ```

4. **Clean everything and start fresh:**
   ```bash
   ./gradlew clean
   rm -rf ~/.gradle/caches/
   rm -rf .gradle/
   rm -rf app/build/
   ./gradlew build
   ```

5. **Report the issue:**
   - Include the full error message
   - Include output of `./gradlew --version`
   - Include relevant logs from Android Studio or command line
