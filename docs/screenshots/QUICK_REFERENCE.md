# Vehicle Sharing App - Quick Visual Reference

This document provides a quick at-a-glance view of all screens in the application.

---

## Screen 1: Welcome (Entry Point)

```
┌───────────────────────────┐
│                           │
│   🚗 Vehicle Sharing 🚕   │
│                           │
│  Share rides, save money  │
│                           │
│   ┌─────────────────┐    │
│   │     LOGIN       │    │
│   └─────────────────┘    │
│   ┌─────────────────┐    │
│   │    SIGN UP      │    │
│   └─────────────────┘    │
│                           │
└───────────────────────────┘
```
**Purpose**: Entry point for all users  
**Actions**: Login or Sign Up

---

## Screen 2: Sign Up (New Users)

```
┌───────────────────────────┐
│   Create Account          │
│                           │
│  [Full Name_________]     │
│  [Email_____________]     │
│  [Password__________]     │
│  [Confirm Password__]     │
│                           │
│   ┌─────────────────┐    │
│   │    SIGN UP      │    │
│   └─────────────────┘    │
│                           │
│          OR               │
│   [🅖 Sign up with ]     │
│   [   Google       ]     │
│                           │
│  Already have account?    │
│        Login              │
└───────────────────────────┘
```
**Purpose**: New user registration  
**Actions**: Fill form and create account

---

## Screen 3: Login (Returning Users)

```
┌───────────────────────────┐
│      Login                │
│                           │
│  [Email_____________]     │
│  [Password__________]     │
│                           │
│   ┌─────────────────┐    │
│   │     LOGIN       │    │
│   └─────────────────┘    │
│                           │
│  Don't have an account?   │
│       Sign up             │
│                           │
└───────────────────────────┘
```
**Purpose**: User authentication  
**Actions**: Enter credentials and login

---

## Screen 4: Browse Trips (Main Screen)

```
┌───────────────────────────────┐
│ Browse Available Trips        │
│                               │
│ [From___] [To___]            │
│ [Show Filters ▼]             │
│                               │
│ ┌───────────────────────┐    │
│ │ SF → LA               │    │
│ │ Nov 5 • 10:00 AM      │    │
│ │ 👤 John • 💺 3        │    │
│ │ 🚭 🎵                 │    │
│ └───────────────────────┘    │
│ ┌───────────────────────┐    │
│ │ Seattle → Portland    │    │
│ │ Nov 6 • 2:00 PM       │    │
│ │ 👤 Jane • 💺 2        │    │
│ │ 🚭 🚫🐕               │    │
│ └───────────────────────┘    │
│                               │
├───────────────────────────────┤
│ 🔍  ➕  📋  👤             │
│Browse Post Hist Prof         │
└───────────────────────────────┘
```
**Purpose**: Browse and filter trips  
**Actions**: Search, filter, view trips

---

## Screen 5: Post Trip (Create New Trip)

```
┌───────────────────────────────┐
│ Post a Trip                   │
│                               │
│ [📍 Origin__________]        │
│ [📍 Destination_____]        │
│ [🕐 Date & Time 📅]          │
│ [Seats Available____]         │
│                               │
│ Preferences:                  │
│ ☑ No Smoking                 │
│ ☑ No Pets                    │
│ ☐ Music Allowed              │
│                               │
│ [Additional Notes]            │
│ [________________]            │
│ [________________]            │
│                               │
│   ┌─────────────────┐        │
│   │   POST TRIP     │        │
│   └─────────────────┘        │
│                               │
├───────────────────────────────┤
│ 🔍  ➕  📋  👤             │
│Browse Post Hist Prof         │
└───────────────────────────────┘
```
**Purpose**: Post new trip  
**Actions**: Fill form, post trip

---

## Screen 6: History (My Trips)

```
┌───────────────────────────────┐
│ My Trips                      │
│                               │
│ ┌───────────────────────┐    │
│ │ ● ACTIVE              │    │
│ │ SF → LA               │    │
│ │ Nov 5, 2025 10:00 AM  │    │
│ │ Seats: 3 available    │    │
│ │ 🚭 No Smoking         │    │
│ │ 🎵 Music OK           │    │
│ └───────────────────────┘    │
│ ┌───────────────────────┐    │
│ │ ● ACTIVE              │    │
│ │ Seattle → Portland    │    │
│ │ Nov 6, 2025 2:00 PM   │    │
│ │ Seats: 2 available    │    │
│ │ 🚭 🚫🐕               │    │
│ └───────────────────────┘    │
│ ┌───────────────────────┐    │
│ │ ○ COMPLETED           │    │
│ │ Oakland → Berkeley    │    │
│ │ Nov 1, 2025 9:00 AM   │    │
│ └───────────────────────┘    │
│                               │
├───────────────────────────────┤
│ 🔍  ➕  📋  👤             │
│Browse Post Hist Prof         │
└───────────────────────────────┘
```
**Purpose**: View your posted trips  
**Actions**: View trip status and details

---

## Screen 7: Profile (Settings)

```
┌───────────────────────────────┐
│ Profile                       │
│                               │
│      👤                       │
│   John Doe                    │
│   john.doe@example.com        │
│                               │
│ Preferences                   │
│                               │
│ Gender Preference:            │
│ ⦿ No Preference              │
│ ○ Same Gender Only           │
│                               │
│ Other Preferences:            │
│ ☑ Prefer No Smoking          │
│ ☐ Prefer No Pets             │
│ ☑ Enjoy Music During Ride    │
│                               │
│   ┌─────────────────┐        │
│   │Save Preferences │        │
│   └─────────────────┘        │
│   ┌─────────────────┐        │
│   │     Logout      │        │
│   └─────────────────┘        │
│                               │
├───────────────────────────────┤
│ 🔍  ➕  📋  👤             │
│Browse Post Hist Prof         │
└───────────────────────────────┘
```
**Purpose**: Manage profile and logout  
**Actions**: Update preferences, logout

---

## Navigation Flow

```
Welcome → Login → Browse Trips
    ↓       ↓          ↕
  Sign Up ─┘      Post Trip
                      ↕
                   History
                      ↕
                   Profile → Logout → Welcome
```

---

## Feature Summary

| Screen | Key Features | Data Saved |
|--------|--------------|------------|
| **Welcome** | Entry point | None |
| **Sign Up** | Create account, validate email | Firebase Auth + Firestore user |
| **Login** | Authenticate | Session token |
| **Browse** | Search, filter, view trips | None (read-only) |
| **Post Trip** | Create trip with preferences | Firestore trip document |
| **History** | View posted trips | None (read-only) |
| **Profile** | Update preferences, logout | Firestore user preferences |

---

## Bottom Navigation (Logged In)

```
┌────────────────────────────────────────┐
│   🔍        ➕        📋        👤     │
│  Browse    Post     History   Profile  │
│   ══════                                │
│  (active)                               │
└────────────────────────────────────────┘
```

Always visible when logged in. Tapping any icon switches to that screen.

---

## Icon Legend

### Navigation Icons
- 🔍 Browse - Search/magnifying glass
- ➕ Post Trip - Plus/add icon
- 📋 History - List/clipboard icon
- 👤 Profile - Person/user icon

### Trip Information Icons
- 📍 Location - Map pin
- 🕐 Time - Clock
- 📅 Date - Calendar
- 💺 Seats - Seat/chair

### Preference Icons
- 🚭 No Smoking - No smoking symbol
- 🚫🐕 No Pets - No pets symbol
- 🎵 Music Allowed - Musical note

### Status Indicators
- ● Active trip - Solid green circle
- ○ Completed trip - Hollow gray circle

---

## State Indicators

### Success
```
✓ Action completed successfully!
```

### Error
```
✗ Error message here
```

### Loading
```
⟳ Loading...
```

### Empty State
```
📋 No items to display
Try creating one!
```

---

## Sample Data Flow

### 1. User Signs Up
```
Welcome → Sign Up → Enter Details → Firebase Auth
                                          ↓
                                    Firestore Users
                                          ↓
                                    Browse Trips
```

### 2. User Posts Trip
```
Post Trip Tab → Fill Form → Submit
                              ↓
                        Firestore Trips
                              ↓
                        History Tab
```

### 3. User Browses Trips
```
Browse Tab → Load Trips ← Firestore Trips (all users)
                ↓
           Filter/Search
                ↓
           Display Results
```

### 4. User Updates Profile
```
Profile Tab → Change Preferences → Save
                                     ↓
                              Firestore Users
                                     ↓
                              ✓ Saved!
```

---

## Complete User Journey in One View

```
1. Launch App → Welcome Screen
   ↓
2. New User → Sign Up → Create Account
   OR
   Returning User → Login → Authenticate
   ↓
3. Browse Trips Tab (default)
   - View all trips
   - Search by location
   - Filter by preferences
   ↓
4. Post Trip Tab
   - Fill trip details
   - Set preferences
   - Post trip
   ↓
5. History Tab
   - View posted trips
   - Check status
   ↓
6. Profile Tab
   - Update preferences
   - Save changes
   - Logout → Welcome
```

---

## Quick Testing Guide

### Test Scenario 1: First Time User
1. Open app → See Welcome
2. Tap Sign Up → Fill form
3. Submit → See Browse screen
4. Bottom nav should be visible

### Test Scenario 2: Post a Trip
1. Login → Tap Post Trip tab
2. Fill all fields
3. Select date/time
4. Tap Post Trip
5. Check History tab → Trip should appear

### Test Scenario 3: Browse and Filter
1. Login → Browse tab
2. Enter origin/destination
3. Tap Show Filters
4. Select preferences
5. Tap Apply Filters
6. Results should filter

### Test Scenario 4: Update Profile
1. Login → Profile tab
2. Change preferences
3. Tap Save Preferences
4. See success message
5. Logout and login again
6. Preferences should persist

---

## Technology Stack Quick Reference

| Component | Technology |
|-----------|-----------|
| Platform | Android |
| Language | Kotlin 1.9.0 |
| Architecture | MVVM |
| UI | Material Design |
| Navigation | Jetpack Navigation |
| Backend | Firebase |
| Auth | Firebase Authentication |
| Database | Cloud Firestore |
| Min SDK | 21 (Android 5.0) |
| Target SDK | 34 (Android 14) |

---

## Firebase Data Structure

### Users Collection
```
users/
  {uid}/
    - fullName: "John Doe"
    - email: "john.doe@example.com"
    - genderPreference: "No Preference"
    - noSmokingPreference: true
    - noPetsPreference: false
    - musicPreference: true
```

### Trips Collection
```
trips/
  {tripId}/
    - driverUid: "user123"
    - driverName: "John Doe"
    - origin: "San Francisco"
    - destination: "Los Angeles"
    - dateTime: 1699200000000
    - seatsAvailable: 3
    - noSmoking: true
    - noPets: true
    - musicAllowed: false
    - status: "active"
```

---

## Summary

This Vehicle Sharing App provides:
- ✅ Complete authentication system
- ✅ Trip posting and management
- ✅ Trip browsing and filtering
- ✅ User profile and preferences
- ✅ Firebase cloud integration
- ✅ Material Design UI
- ✅ Intuitive navigation

**Total Screens**: 7  
**Main Features**: 5 (Auth, Post, Browse, History, Profile)  
**Backend**: Firebase (Auth + Firestore)  
**Status**: Phase 1 Complete ✓

---

For detailed information, see:
- [VISUAL_MOCKUPS.md](VISUAL_MOCKUPS.md) - Detailed screen layouts
- [USER_JOURNEY.md](USER_JOURNEY.md) - Complete user journeys
- [README.md](README.md) - Documentation overview
