# NTU Vehicle Sharing App - Documentation

This directory contains comprehensive documentation for the NTU Vehicle Sharing application.

## 📋 Quick Links

### New to the Project?
- **[APP_USER_FLOW.md](APP_USER_FLOW.md)** - Start here! Complete walkthrough of the app from welcome screen to all features
- **[BROWSE_FEATURE_VISUAL_GUIDE.md](BROWSE_FEATURE_VISUAL_GUIDE.md)** - Visual guide showing the Browse feature in detail

### Planning & Requirements
- **[requirements.md](requirements.md)** - Functional and non-functional requirements
- **[features_scope.md](features_scope.md)** - Features and project scope
- **[literature_review.md](literature_review.md)** - Review of existing ridesharing solutions

### Technical Documentation
- **[system_architecture.md](system_architecture.md)** - System design and architecture
- **[database_schema.md](database_schema.md)** - Firebase Firestore schema and security rules
- **[firebase_setup.md](firebase_setup.md)** - Firebase configuration guide

### Implementation Guides
- **[feature_implementation.md](feature_implementation.md)** - Complete feature implementation summary
- **[ntu_features.md](ntu_features.md)** - NTU-specific features documentation
- **[quick_reference.md](quick_reference.md)** - Quick reference guide

### Troubleshooting
- **[GRADLE_FIX.md](GRADLE_FIX.md)** - Gradle build configuration fixes

## 🎯 Recent Updates

### Browse Feature
The Browse Trips feature allows students to discover and book rides. See:
- **[APP_USER_FLOW.md](APP_USER_FLOW.md)** - Complete walkthrough including Browse functionality
- **[BROWSE_FEATURE_VISUAL_GUIDE.md](BROWSE_FEATURE_VISUAL_GUIDE.md)** - Detailed visual guide

**Key Features:**
- ✅ View all available trips
- ✅ Filter by location and preferences
- ✅ See driver badges (hall, club, course)
- ✅ Book rides with checkout process
- ✅ Real-time seat availability

## 📱 App Features Overview

### Core Features
1. **User Authentication** - NTU email (@e.ntu.edu.sg) validation
2. **Browse Trips** - Find available rides with filtering
3. **Post Trips** - Create carpooling opportunities
4. **User Profiles** - NTU badges (hall, club, cohort)
5. **Trip History** - Past rides tracking

### NTU-Specific Features
- 🎓 NTU-only access (@e.ntu.edu.sg emails)
- 🏫 50+ preloaded campus locations
- 🏠 Hall resident badges
- 👥 Club membership badges
- 📚 Course cohort display
- 📞 Quick dial to NTU Security (6791 1616)

## 🏗️ Architecture

```
┌─────────────────────────────────────────┐
│           Android App (Kotlin)          │
├─────────────────────────────────────────┤
│  Welcome → Login/Signup → Main App     │
│  [History] [Browse] [Post] [Profile]   │
└─────────────────────────────────────────┘
                    ↕
┌─────────────────────────────────────────┐
│         Firebase Services               │
├─────────────────────────────────────────┤
│  • Authentication (Email/Password)      │
│  • Firestore Database (trips, users)   │
│  • Storage (future: profile pictures)  │
└─────────────────────────────────────────┘
```

## 🛠️ Technology Stack

- **Platform**: Android
- **Language**: Kotlin 1.9.0
- **Architecture**: MVVM (Model-View-ViewModel)
- **Backend**: Firebase (Authentication, Firestore, Storage)
- **UI**: Material Design Components, Jetpack Navigation
- **Min SDK**: 23 (Android 6.0)
- **Target SDK**: 34 (Android 14)

## 📖 Documentation Guide

### For Developers
1. Start with [system_architecture.md](system_architecture.md)
2. Review [database_schema.md](database_schema.md)
3. Follow [firebase_setup.md](firebase_setup.md) for setup
4. Check [feature_implementation.md](feature_implementation.md) for features

### For Users
1. Read [APP_USER_FLOW.md](APP_USER_FLOW.md) for complete walkthrough
2. See [BROWSE_FEATURE_VISUAL_GUIDE.md](BROWSE_FEATURE_VISUAL_GUIDE.md) for Browse feature
3. Check [ntu_features.md](ntu_features.md) for NTU-specific features

### For Project Management
1. Review [requirements.md](requirements.md)
2. Check [features_scope.md](features_scope.md)
3. See [literature_review.md](literature_review.md)

## 🚀 Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/serenentu/vehicle-sharing-app.git
   ```

2. **Set up Firebase**
   - Follow [firebase_setup.md](firebase_setup.md)
   - Replace `app/google-services.json` with your Firebase config

3. **Build and run**
   ```bash
   ./gradlew build
   ```
   See [GRADLE_FIX.md](GRADLE_FIX.md) if you encounter build issues

## 📞 Support

For issues or questions:
- Check the relevant documentation above
- Review [troubleshooting section in README](../README.md#troubleshooting)
- See [FIRESTORE_RULES_DEPLOYMENT.md](FIRESTORE_RULES_DEPLOYMENT.md) for Firebase setup issues

## 📝 Contributing

When adding new documentation:
1. Use clear, descriptive filenames
2. Add entry to this README
3. Follow existing documentation style
4. Include visual diagrams where helpful
5. Keep technical and user documentation separate

## 🔄 Recent Changes

- **2024-11-08**: Added APP_USER_FLOW.md and BROWSE_FEATURE_VISUAL_GUIDE.md
- **2024-11-08**: Fixed critical Browse Trips crash issue
- See git history for complete changelog

---

**Note**: All documentation uses ASCII art and text-based diagrams for universal accessibility and version control friendliness.
