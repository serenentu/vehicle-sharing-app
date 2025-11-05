# Firebase Setup Guide

This guide will help you set up Firebase for the Vehicle Sharing App.

## Prerequisites

- A Google account
- Android Studio installed
- The Vehicle Sharing App project open in Android Studio

## Step 1: Create a Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click "Add project"
3. Enter a project name (e.g., "Vehicle Sharing App")
4. (Optional) Enable Google Analytics
5. Click "Create project"

## Step 2: Add Android App to Firebase

1. In the Firebase Console, click the Android icon to add an Android app
2. Enter the package name: `com.serenentu.vehiclesharing`
3. (Optional) Enter an app nickname: "Vehicle Sharing App"
4. (Optional) Add your SHA-1 certificate fingerprint for Google Sign-In
5. Click "Register app"

### Getting SHA-1 Fingerprint (for Google Sign-In)

Run this command in your terminal:

```bash
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```

Copy the SHA-1 fingerprint and paste it in Firebase Console.

## Step 3: Download google-services.json

1. Download the `google-services.json` file from Firebase Console
2. Move it to the `app/` directory of your project
3. Replace the existing placeholder file at `app/google-services.json`

**Important**: The placeholder `google-services.json` file in the repository will NOT work. You must replace it with your own.

## Step 4: Enable Authentication

1. In Firebase Console, go to "Build" → "Authentication"
2. Click "Get started"
3. Select "Email/Password" as a sign-in method
4. Enable "Email/Password"
5. Click "Save"

### Optional: Enable Google Sign-In

1. In the Authentication section, select "Google"
2. Enable Google Sign-In
3. Enter a support email
4. Click "Save"

## Step 5: Create Firestore Database

1. In Firebase Console, go to "Build" → "Firestore Database"
2. Click "Create database"
3. Select "Start in test mode" (for development)
4. Choose a location for your database (select closest to your users)
5. Click "Enable"

### Important: Update Security Rules (Before Production)

For development, test mode allows all reads and writes. Before deploying to production, update your Firestore security rules:

1. Go to "Firestore Database" → "Rules"
2. Replace the rules with the following:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Users can read and write their own user document
    match /users/{userId} {
      allow read: if request.auth != null;
      allow write: if request.auth != null && request.auth.uid == userId;
    }
    
    // Anyone authenticated can read active trips
    match /trips/{tripId} {
      allow read: if request.auth != null;
      allow create: if request.auth != null && request.auth.uid == request.resource.data.driverUid;
      allow update, delete: if request.auth != null && request.auth.uid == resource.data.driverUid;
    }
  }
}
```

3. Click "Publish"

## Step 6: Build and Run the App

1. Open the project in Android Studio
2. Sync Gradle files: File → Sync Project with Gradle Files
3. Connect an Android device or start an emulator
4. Click Run (or press Shift + F10)

## Step 7: Test the App

1. **Sign Up**:
   - Open the app
   - Click "Sign Up"
   - Enter your details
   - Click "Sign Up"

2. **Post a Trip**:
   - Navigate to "Post Trip" tab
   - Fill in trip details
   - Click "Post Trip"

3. **Browse Trips**:
   - Navigate to "Browse" tab
   - View available trips
   - Use filters to search

4. **View Profile**:
   - Navigate to "Profile" tab
   - Update your preferences
   - Click "Save Preferences"

## Troubleshooting

### Problem: "google-services.json not found"

**Solution**: Make sure you've downloaded and placed the `google-services.json` file in the `app/` directory.

### Problem: "Failed to authenticate"

**Solution**: 
- Verify Email/Password authentication is enabled in Firebase Console
- Check that your Firebase project is properly configured
- Ensure your app package name matches the one in Firebase

### Problem: "Failed to load trips"

**Solution**:
- Verify Firestore is enabled in Firebase Console
- Check that test mode is enabled (or security rules are correct)
- Ensure you're connected to the internet

### Problem: "SHA-1 fingerprint required" (for Google Sign-In)

**Solution**: Follow the steps in "Step 2: Add Android App to Firebase" to add your SHA-1 fingerprint.

## Additional Resources

- [Firebase Documentation](https://firebase.google.com/docs)
- [Firebase Android Setup](https://firebase.google.com/docs/android/setup)
- [Firestore Getting Started](https://firebase.google.com/docs/firestore/quickstart)
- [Firebase Authentication](https://firebase.google.com/docs/auth)

## Support

If you encounter any issues, please check:
1. Firebase Console for any error messages
2. Android Studio Logcat for detailed error logs
3. Ensure all dependencies in `build.gradle` are up to date

## Security Notes

- Never commit your actual `google-services.json` file to a public repository
- Use environment-specific configurations for development and production
- Implement proper security rules before deploying to production
- Regularly review Firebase Console for security alerts
