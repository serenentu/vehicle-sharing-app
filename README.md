# Vehicle Sharing App – FYP (Part 1, Semester 1)

This is the repository for the Vehicle Sharing App developed as a Final Year Project (FYP). The app is built with Android Studio using Kotlin and integrates Firebase for backend services.

## Project Overview

The Vehicle Sharing App aims to facilitate carpooling and vehicle sharing among users, connecting drivers with passengers for shared travel. The app emphasizes safety, user preferences, and real-time ride matching.

### Key Features

1. **User Authentication** - Email and social media sign-up/login
2. **Trip Posting & Browsing** - Post and search for available rides
3. **User Profiles** - Manage preferences (gender, smoking, pets, music)
4. **Filtering System** - Filter trips based on user preferences
5. **Firebase Integration** - Real-time data synchronization and storage

### Future Features (Phase 2)
- Matching algorithm based on location and time
- In-app chat and notifications
- Ratings and reviews system
- Google Maps integration

## Timeline (Semester 1)

- **Weeks 1–2:** Literature review, requirements, features scope
- **Weeks 3–4:** System architecture, data model, ERD/schema
- **Weeks 5–7:** UI mockups, design diagrams, finalize scope
- **Weeks 8–13:** Development (authentication, profile, rides, DB integration)
- **Week 13:** Interim report & video

## Technology Stack

- **Platform**: Android
- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Backend**: Firebase (Authentication, Firestore, Storage)
- **UI**: Material Design Components, Jetpack Navigation
- **Minimum SDK**: 21 (Android 5.0)
- **Target SDK**: 33 (Android 13)

## Setup Instructions

### Prerequisites

1. Android Studio (Arctic Fox or later)
2. JDK 8 or higher
3. Android SDK with API 33
4. Firebase account

### Firebase Setup

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project or use an existing one
3. Add an Android app to your Firebase project
   - Package name: `com.serenentu.vehiclesharing`
4. Download the `google-services.json` file
5. Replace the placeholder file at `app/google-services.json` with your downloaded file
6. Enable Authentication in Firebase Console:
   - Enable Email/Password authentication
   - (Optional) Enable Google Sign-In
7. Create a Firestore Database:
   - Start in test mode for development
   - Later, implement the security rules from `docs/database_schema.md`

### Project Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/serenentu/vehicle-sharing-app.git
   cd vehicle-sharing-app
   ```

2. Open the project in Android Studio

3. Replace `app/google-services.json` with your Firebase configuration file

4. Sync Gradle files:
   - File → Sync Project with Gradle Files

5. Build and run the app:
   - Select an emulator or connected device
   - Click Run (or press Shift + F10)

### Project Structure

```
vehicle-sharing-app/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/serenentu/vehiclesharing/
│   │       │   ├── data/model/          # Data models
│   │       │   ├── MainActivity.kt       # Main activity
│   │       │   ├── WelcomeFragment.kt   # Welcome screen
│   │       │   ├── LoginFragment.kt     # Login screen
│   │       │   ├── SignupFragment.kt    # Signup screen
│   │       │   ├── HistoryFragment.kt   # Trip history
│   │       │   ├── PostTripFragment.kt  # Post trip
│   │       │   └── ProfileFragment.kt   # User profile
│   │       ├── res/
│   │       │   ├── layout/              # XML layouts
│   │       │   ├── navigation/          # Navigation graph
│   │       │   └── menu/                # Bottom navigation menu
│   │       └── AndroidManifest.xml
│   ├── build.gradle                      # App-level Gradle config
│   └── google-services.json             # Firebase config (replace with yours)
├── docs/                                 # Project documentation
│   ├── requirements.md
│   ├── features_scope.md
│   ├── literature_review.md
│   ├── system_architecture.md           # Architecture documentation
│   └── database_schema.md               # Database schema
├── build.gradle                          # Project-level Gradle config
├── settings.gradle                       # Gradle settings
└── README.md                             # This file
```

## Documentation

See the `/docs` folder for detailed project documentation:

- **[requirements.md](docs/requirements.md)** - Functional and non-functional requirements
- **[features_scope.md](docs/features_scope.md)** - Features and project scope
- **[literature_review.md](docs/literature_review.md)** - Review of existing solutions
- **[system_architecture.md](docs/system_architecture.md)** - Detailed system architecture
- **[database_schema.md](docs/database_schema.md)** - Firebase Firestore schema and security rules
- **[firebase_setup.md](docs/firebase_setup.md)** - Step-by-step Firebase setup guide
- **[feature_implementation.md](docs/feature_implementation.md)** - Complete feature implementation summary

## Development Guidelines

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add comments for complex logic

### Git Workflow
- Create feature branches for new features
- Write descriptive commit messages
- Test before pushing to main branch

### Testing
- Test on different screen sizes
- Test with different Android versions (minimum API 21)
- Verify Firebase operations work correctly

## Troubleshooting

### Common Issues

1. **Build fails with Firebase error**
   - Ensure `google-services.json` is properly configured
   - Check that Firebase project package name matches app package name

2. **Navigation doesn't work**
   - Verify all fragments are registered in `nav_graph.xml`
   - Check that fragment IDs match between navigation and menu files

3. **Bottom navigation not showing**
   - Check MainActivity's navigation setup
   - Verify bottom navigation visibility logic

## Contributors

- [Your Name] – Student Developer
- [Supervisor Name] – Project Supervisor

## License

This project is developed for academic purposes as part of a Final Year Project.

## Contact

For questions or issues, please contact [your-email@example.com]