# System Architecture Documentation

## Overview

The Vehicle Sharing App is an Android application built using modern Android development practices and follows the MVVM (Model-View-ViewModel) architecture pattern.

## Technology Stack

### Frontend
- **Platform**: Android
- **Language**: Kotlin
- **Minimum SDK**: 21 (Android 5.0 Lollipop)
- **Target SDK**: 33 (Android 13)

### Backend
- **Firebase Authentication**: User sign-up and login
- **Firebase Firestore**: NoSQL cloud database for real-time data
- **Firebase Storage**: Cloud storage for user profile images (future)

### Android Libraries
- **Jetpack Navigation**: Fragment navigation and deep linking
- **Material Design Components**: UI components following Material Design guidelines
- **AndroidX Lifecycle**: ViewModel and LiveData for lifecycle-aware components
- **Room Database**: Local caching and offline support (future)
- **Coroutines**: Asynchronous programming

## Architecture Pattern: MVVM

```
┌─────────────────────────────────────────────────────────┐
│                    Presentation Layer                    │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐    │
│  │  Fragments  │  │  Activities │  │   Adapters  │    │
│  └──────┬──────┘  └──────┬──────┘  └──────┬──────┘    │
│         │                 │                 │            │
│         └─────────────────┴─────────────────┘            │
│                           │                               │
└───────────────────────────┼───────────────────────────────┘
                            │
┌───────────────────────────┼───────────────────────────────┐
│                    ViewModel Layer                        │
│                           │                               │
│  ┌─────────────────────────────────────────────────┐    │
│  │            ViewModels (Future)                   │    │
│  │  - UserViewModel                                 │    │
│  │  - TripViewModel                                 │    │
│  │  - AuthViewModel                                 │    │
│  └─────────────────────┬───────────────────────────┘    │
└────────────────────────┼──────────────────────────────────┘
                         │
┌────────────────────────┼──────────────────────────────────┐
│                    Data Layer                             │
│                         │                                 │
│  ┌──────────────────────┴────────────────────────────┐  │
│  │              Repositories (Future)                 │  │
│  │  - UserRepository                                  │  │
│  │  - TripRepository                                  │  │
│  └──────────────────┬──────────────┬──────────────────┘  │
│                     │              │                      │
│  ┌─────────────────┴──┐  ┌────────┴────────────┐        │
│  │  Firebase           │  │  Room Database      │        │
│  │  (Remote Data)      │  │  (Local Cache)      │        │
│  └────────────────────┘  └─────────────────────┘        │
└──────────────────────────────────────────────────────────┘
```

## Application Components

### 1. Activities
- **MainActivity**: Single activity hosting all fragments

### 2. Fragments
- **WelcomeFragment**: Landing screen with login/signup options
- **LoginFragment**: User authentication via email
- **SignupFragment**: New user registration
- **HistoryFragment**: View past and upcoming trips
- **PostTripFragment**: Create new trip offerings
- **ProfileFragment**: User profile and preferences management

### 3. Data Models
- **User**: User profile and preferences
- **Trip**: Trip information and details

### 4. Navigation
- Uses Android Navigation Component
- Single Activity with Fragment-based navigation
- Bottom Navigation Bar for main app sections

## Key Features Implementation

### Phase 1 (Current - Semester 1)

#### 1. User Authentication
- Email-based authentication via Firebase Auth
- Google Sign-In integration (placeholder)
- User profile creation with preferences

#### 2. Trip Posting & Browsing
- Post travel plans with origin, destination, and time
- Specify preferences (smoking, pets, music)
- Browse available rides (basic implementation)

#### 3. User Profiles
- Profile management
- Preference settings:
  - Gender preference
  - Smoking preference
  - Pets preference
  - Music preference

### Phase 2 (Future - Semester 2)

#### 4. Matching Algorithm
- Filter trips by user preferences
- Location-based matching
- Time-based matching

#### 5. Chat & Notifications
- In-app messaging between drivers and passengers
- Push notifications for ride updates
- WhatsApp integration

#### 6. Ratings & Reviews
- Post-ride rating system
- User reviews and feedback
- Rating-based trust system

#### 7. Map Integration
- Google Maps integration
- Route visualization
- Pickup point selection
- Estimated time calculation

## Data Flow

### Trip Posting Flow
```
User Input → PostTripFragment → Firebase Firestore → Trips Collection
                                      ↓
                            Real-time Update
                                      ↓
                            HistoryFragment (Refresh)
```

### User Authentication Flow
```
User Credentials → LoginFragment → Firebase Auth → Success
                                          ↓
                                    Create/Update User Document
                                          ↓
                                    Navigate to HistoryFragment
```

## Security Considerations

1. **Firebase Authentication**: Secure user authentication
2. **Firestore Security Rules**: Restrict data access based on authentication
3. **Input Validation**: Validate all user inputs
4. **HTTPS**: All Firebase communication is encrypted

## Scalability

### Current Phase
- Direct Firebase integration in Fragments
- Suitable for Phase 1 development and testing

### Future Enhancement (Phase 2)
- Implement Repository pattern for data abstraction
- Add ViewModel layer for better state management
- Implement Room database for offline caching
- Add dependency injection (Hilt/Dagger)

## Testing Strategy

### Phase 1
- Manual testing of UI flows
- Firebase integration testing

### Phase 2 (Future)
- Unit tests for ViewModels and Repositories
- UI tests using Espresso
- Integration tests for Firebase operations

## Build Configuration

### Gradle Dependencies
- Navigation Component
- Material Design
- Firebase BOM (Bill of Materials)
- Firebase Auth
- Firebase Firestore
- Kotlin Coroutines

### Build Variants
- Debug: Development build with Firebase test project
- Release: Production build with ProGuard enabled
