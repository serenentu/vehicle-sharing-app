# Running the Vehicle Sharing App - Complete Guide

This guide provides step-by-step instructions on how to run the Vehicle Sharing App and showcases what you can expect at each stage of the application.

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Setup Instructions](#setup-instructions)
3. [Running the App](#running-the-app)
4. [Application Walkthrough](#application-walkthrough)
5. [Expected Screens and Features](#expected-screens-and-features)

## Prerequisites

Before running the application, ensure you have:

- **Android Studio** (Hedgehog 2023.1.1 or later)
- **JDK 8 or higher**
- **Android SDK** with API 21-34
- **Firebase Account** (for backend services)
- **Physical Android Device** or **Android Emulator**

## Setup Instructions

### Step 1: Clone the Repository

```bash
git clone https://github.com/serenentu/vehicle-sharing-app.git
cd vehicle-sharing-app
```

### Step 2: Configure Firebase

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project or select existing one
3. Add an Android app:
   - Package name: `com.serenentu.vehiclesharing`
4. Download `google-services.json`
5. Replace the file at `app/google-services.json`

### Step 3: Enable Firebase Services

In your Firebase Console:

1. **Authentication**:
   - Go to Authentication → Sign-in method
   - Enable Email/Password authentication
   - (Optional) Enable Google Sign-In

2. **Firestore Database**:
   - Go to Firestore Database
   - Create database in test mode (for development)
   - Note: For production, implement security rules from `docs/database_schema.md`

### Step 4: Open in Android Studio

1. Launch Android Studio
2. Select "Open an Existing Project"
3. Navigate to the cloned repository
4. Click "OK"

### Step 5: Sync Gradle

Android Studio will automatically prompt you to sync Gradle files. If not:
- Go to **File → Sync Project with Gradle Files**

### Step 6: Build the Project

Option 1 - Using Android Studio:
- Click the **Run** button (green play icon)
- Or press **Shift + F10**

Option 2 - Using Command Line:
```bash
./gradlew assembleDebug
```

## Running the App

### Option 1: Using Android Emulator

1. In Android Studio, click **Device Manager** (phone icon in toolbar)
2. Create a new virtual device:
   - Choose a device definition (e.g., Pixel 5)
   - Select a system image (API 30 or higher recommended)
   - Finish setup
3. Launch the emulator
4. Click **Run** in Android Studio

### Option 2: Using Physical Device

1. Enable **Developer Options** on your Android device:
   - Go to Settings → About Phone
   - Tap "Build Number" 7 times
2. Enable **USB Debugging**:
   - Go to Settings → Developer Options
   - Toggle on "USB Debugging"
3. Connect device via USB
4. Authorize USB debugging on the device
5. Select your device in Android Studio and click **Run**

## Application Walkthrough

### Complete User Journey

This section describes the complete flow through the application from first launch to using all features.

---

## Expected Screens and Features

### Screen 1: Welcome Screen (App Launch)

**What You'll See:**
```
┌─────────────────────────────────┐
│                                 │
│    Vehicle Sharing App          │
│        (Title)                  │
│                                 │
│  Share rides, save money,       │
│   reduce emissions              │
│                                 │
│    ┌─────────────────┐         │
│    │     Login       │         │
│    └─────────────────┘         │
│                                 │
│    ┌─────────────────┐         │
│    │    Sign Up      │         │
│    └─────────────────┘         │
│                                 │
└─────────────────────────────────┘
```

**Description:**
- Clean, centered layout with app branding
- Two prominent buttons for Login and Sign Up
- This is the entry point for all new and returning users
- No authentication required to view this screen

**Actions Available:**
- Tap "Login" → Navigate to Login screen
- Tap "Sign Up" → Navigate to Sign Up screen

---

### Screen 2: Sign Up Screen

**What You'll See:**
```
┌─────────────────────────────────┐
│    Create Account               │
│                                 │
│    ┌─────────────────────────┐ │
│    │ Full Name               │ │
│    └─────────────────────────┘ │
│                                 │
│    ┌─────────────────────────┐ │
│    │ Email                   │ │
│    └─────────────────────────┘ │
│                                 │
│    ┌─────────────────────────┐ │
│    │ Password                │ │
│    └─────────────────────────┘ │
│                                 │
│    ┌─────────────────────────┐ │
│    │ Confirm Password        │ │
│    └─────────────────────────┘ │
│                                 │
│    ┌─────────────────────────┐ │
│    │      Sign Up            │ │
│    └─────────────────────────┘ │
│                                 │
│            OR                   │
│                                 │
│    ┌─────────────────────────┐ │
│    │  Sign up with Google    │ │
│    └─────────────────────────┘ │
│                                 │
│   Already have an account?      │
│         Login                   │
└─────────────────────────────────┘
```

**Description:**
- Scrollable form for new user registration
- Input validation for all fields
- Password strength checking
- Confirmation password matching

**Form Fields:**
1. **Full Name** - Text input for user's full name
2. **Email** - Email format validation
3. **Password** - Minimum length validation
4. **Confirm Password** - Must match password field

**Actions Available:**
- Fill in all fields and tap "Sign Up" → Creates account and navigates to main app
- Tap "Sign up with Google" → Opens Google Sign-In (placeholder)
- Tap "Already have an account? Login" → Navigate to Login screen

**Expected Behavior:**
- On successful signup:
  - User account created in Firebase Authentication
  - User profile created in Firestore database
  - Navigate to History screen with bottom navigation visible
- On error:
  - Display error message (e.g., "Email already in use")
  - Keep user on signup screen

---

### Screen 3: Login Screen

**What You'll See:**
```
┌─────────────────────────────────┐
│                                 │
│         Login                   │
│                                 │
│    ┌─────────────────────────┐ │
│    │ Email                   │ │
│    └─────────────────────────┘ │
│                                 │
│    ┌─────────────────────────┐ │
│    │ Password                │ │
│    └─────────────────────────┘ │
│                                 │
│    ┌─────────────────────────┐ │
│    │       Login             │ │
│    └─────────────────────────┘ │
│                                 │
│   Don't have an account?        │
│        Sign up                  │
│                                 │
└─────────────────────────────────┘
```

**Description:**
- Simple, clean login form
- Email and password authentication
- Link to signup for new users

**Form Fields:**
1. **Email** - Email address for login
2. **Password** - User password

**Actions Available:**
- Enter credentials and tap "Login" → Authenticate and enter app
- Tap "Don't have an account? Sign up" → Navigate to Sign Up screen

**Expected Behavior:**
- On successful login:
  - Firebase authenticates credentials
  - Navigate to History screen with bottom navigation visible
- On error:
  - Display error message (e.g., "Invalid email or password")
  - Keep user on login screen

---

### Screen 4: Browse Trips (Main Screen - Tab 1)

**What You'll See:**
```
┌─────────────────────────────────┐
│  Browse Available Trips         │
│                                 │
│  ┌──────────┐  ┌──────────┐   │
│  │  From    │  │    To    │   │
│  └──────────┘  └──────────┘   │
│                                 │
│  ┌─────────────────────────┐   │
│  │    Show Filters         │   │
│  └─────────────────────────┘   │
│                                 │
│  ┌─────────────────────────┐   │
│  │ Trip Card 1             │   │
│  │ Origin → Destination    │   │
│  │ Date/Time               │   │
│  │ Seats: 3  Driver: Name  │   │
│  │ 🚭 No Smoking           │   │
│  └─────────────────────────┘   │
│                                 │
│  ┌─────────────────────────┐   │
│  │ Trip Card 2             │   │
│  │ Origin → Destination    │   │
│  │ Date/Time               │   │
│  │ Seats: 2  Driver: Name  │   │
│  │ 🎵 Music Allowed        │   │
│  └─────────────────────────┘   │
│                                 │
├─────────────────────────────────┤
│  🔍    ➕    📋    👤         │
│ Browse Post History Profile    │
└─────────────────────────────────┘
```

**Description:**
- Main browsing interface for available trips
- Search by origin and destination
- Filter panel (expandable) for preferences
- RecyclerView list of trip cards
- Bottom navigation bar always visible

**Features:**
1. **Search Fields**:
   - From (Origin)
   - To (Destination)

2. **Filter Panel** (expandable):
   - No Smoking checkbox
   - No Pets checkbox
   - Music Allowed checkbox
   - Apply Filters button

3. **Trip Cards** showing:
   - Origin and destination
   - Date and time of trip
   - Number of available seats
   - Driver name
   - Preference icons (no smoking, no pets, music)
   - Additional notes (if any)

**Actions Available:**
- Enter origin/destination and search
- Tap "Show Filters" to expand filter options
- Apply filters to narrow down results
- Tap any trip card to view details (placeholder)
- Use bottom navigation to switch screens

**Expected Behavior:**
- On app load: Fetches all active trips from Firestore
- When searching: Filters trips by origin/destination (case-insensitive)
- When applying filters: Shows only trips matching selected preferences
- If no trips found: Shows "No trips available" message

---

### Screen 5: Post Trip (Tab 2)

**What You'll See:**
```
┌─────────────────────────────────┐
│  Post a Trip                    │
│                                 │
│  Trip Details                   │
│                                 │
│  ┌─────────────────────────┐   │
│  │ 📍 Origin               │   │
│  └─────────────────────────┘   │
│                                 │
│  ┌─────────────────────────┐   │
│  │ 📍 Destination          │   │
│  └─────────────────────────┘   │
│                                 │
│  ┌─────────────────────────┐   │
│  │ 🕐 Date and Time        │   │
│  └─────────────────────────┘   │
│                                 │
│  ┌─────────────────────────┐   │
│  │ Seats Available         │   │
│  └─────────────────────────┘   │
│                                 │
│  Preferences                    │
│                                 │
│  ☑ No Smoking                  │
│  ☑ No Pets                     │
│  ☐ Music Allowed               │
│                                 │
│  ┌─────────────────────────┐   │
│  │ Additional Notes        │   │
│  │ (multiline text box)    │   │
│  │                         │   │
│  └─────────────────────────┘   │
│                                 │
│  ┌─────────────────────────┐   │
│  │      Post Trip          │   │
│  └─────────────────────────┘   │
│                                 │
├─────────────────────────────────┤
│  🔍    ➕    📋    👤         │
│ Browse Post History Profile    │
└─────────────────────────────────┘
```

**Description:**
- Form for drivers to post new trips
- All required trip information
- Preference checkboxes
- Date/time picker integration

**Form Fields:**
1. **Origin** - Starting location (text input)
2. **Destination** - End location (text input)
3. **Date and Time** - Trip date/time (opens picker on tap)
4. **Seats Available** - Number of seats (numeric input)
5. **Preferences** (checkboxes):
   - No Smoking
   - No Pets
   - Music Allowed
6. **Additional Notes** - Free text area for extra info

**Actions Available:**
- Fill in all required fields
- Tap date/time field → Opens Android date/time picker
- Check/uncheck preference boxes
- Tap "Post Trip" → Save to Firestore and return to history

**Expected Behavior:**
- On tapping date/time field: Opens native Android picker
- Form validation on submit:
  - All fields must be filled
  - Seats must be > 0
  - Date/time must be in the future
- On successful post:
  - Trip saved to Firestore with status "active"
  - Success message displayed
  - Form cleared for new entry
- On error:
  - Error message displayed (e.g., "Please fill all fields")

---

### Screen 6: History / My Trips (Tab 3)

**What You'll See:**
```
┌─────────────────────────────────┐
│  My Trips                       │
│                                 │
│  ┌─────────────────────────┐   │
│  │ ACTIVE                  │   │
│  │ San Francisco → LA      │   │
│  │ Nov 5, 2025 10:00 AM    │   │
│  │ Seats: 3 available      │   │
│  │ No Smoking, Music OK    │   │
│  └─────────────────────────┘   │
│                                 │
│  ┌─────────────────────────┐   │
│  │ ACTIVE                  │   │
│  │ Seattle → Portland      │   │
│  │ Nov 6, 2025 2:00 PM     │   │
│  │ Seats: 2 available      │   │
│  │ No Smoking, No Pets     │   │
│  └─────────────────────────┘   │
│                                 │
│  ┌─────────────────────────┐   │
│  │ COMPLETED               │   │
│  │ Oakland → Berkeley      │   │
│  │ Nov 1, 2025 9:00 AM     │   │
│  │ Seats: 1 was available  │   │
│  └─────────────────────────┘   │
│                                 │
├─────────────────────────────────┤
│  🔍    ➕    📋    👤         │
│ Browse Post History Profile    │
└─────────────────────────────────┘
```

**Description:**
- Shows all trips posted by the current user
- Displays trip status (Active/Completed)
- Chronologically ordered (newest first)
- Read-only view of user's trip history

**Trip Card Information:**
- Trip status (ACTIVE, COMPLETED)
- Origin → Destination
- Date and time
- Seats available/was available
- Preferences (shown as text)

**Actions Available:**
- Scroll through trip history
- View trip details (currently read-only)
- Use bottom navigation to switch screens

**Expected Behavior:**
- On screen load:
  - Fetches all trips where driverUid matches current user
  - Orders by date (newest first)
  - Groups by status (active trips first)
- If no trips: Shows "No trips posted yet"

---

### Screen 7: Profile (Tab 4)

**What You'll See:**
```
┌─────────────────────────────────┐
│  Profile                        │
│                                 │
│  John Doe                       │
│  john.doe@example.com           │
│                                 │
│  Preferences                    │
│                                 │
│  Gender Preference              │
│  ⦿ No Preference               │
│  ○ Same Gender Only            │
│                                 │
│  Other Preferences              │
│  ☑ Prefer No Smoking           │
│  ☐ Prefer No Pets              │
│  ☑ Enjoy Music During Ride     │
│                                 │
│  ┌─────────────────────────┐   │
│  │  Save Preferences       │   │
│  └─────────────────────────┘   │
│                                 │
│  ┌─────────────────────────┐   │
│  │      Logout             │   │
│  └─────────────────────────┘   │
│                                 │
├─────────────────────────────────┤
│  🔍    ➕    📋    👤         │
│ Browse Post History Profile    │
└─────────────────────────────────┘
```

**Description:**
- User profile management screen
- Display user information
- Set riding preferences
- Logout functionality

**Information Displayed:**
1. **User Name** - Full name from profile
2. **Email Address** - User's email

**Preferences:**
1. **Gender Preference** (Radio buttons):
   - No Preference (default)
   - Same Gender Only

2. **Other Preferences** (Checkboxes):
   - Prefer No Smoking
   - Prefer No Pets
   - Enjoy Music During Ride

**Actions Available:**
- Select gender preference (radio buttons)
- Check/uncheck other preferences
- Tap "Save Preferences" → Update Firestore profile
- Tap "Logout" → Sign out and return to welcome screen

**Expected Behavior:**
- On screen load:
  - Fetches user data from Firestore
  - Pre-fills all preference selections
- On save:
  - Updates user document in Firestore
  - Shows success message
  - Preferences persist across sessions
- On logout:
  - Signs out from Firebase Auth
  - Clears session
  - Navigates to Welcome screen
  - Hides bottom navigation

---

## Navigation Flow Summary

```
Welcome Screen
    ├─→ Login Screen → Main App (Browse Trips)
    │                    ├─→ Browse Trips Tab
    │                    ├─→ Post Trip Tab
    │                    ├─→ History Tab
    │                    └─→ Profile Tab → Logout → Welcome
    │
    └─→ Sign Up Screen → Main App (Browse Trips)
                         └─→ (same as above)
```

## Bottom Navigation

Once logged in, the bottom navigation is always visible with 4 tabs:

1. **🔍 Browse** - Browse available trips
2. **➕ Post Trip** - Post a new trip
3. **📋 History** - View your posted trips
4. **👤 Profile** - Manage profile and preferences

## Data Persistence

The app uses Firebase for backend services:

### Firebase Authentication
- Handles user registration and login
- Maintains user session
- Supports email/password authentication

### Cloud Firestore Collections

**users/** collection:
```json
{
  "uid": "user123",
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "genderPreference": "No Preference",
  "noSmokingPreference": true,
  "noPetsPreference": false,
  "musicPreference": true,
  "rating": 0.0,
  "totalRides": 0
}
```

**trips/** collection:
```json
{
  "tripId": "trip123",
  "driverUid": "user123",
  "driverName": "John Doe",
  "origin": "San Francisco",
  "destination": "Los Angeles",
  "dateTime": 1699200000000,
  "seatsAvailable": 3,
  "noSmoking": true,
  "noPets": true,
  "musicAllowed": true,
  "additionalNotes": "Highway 1 scenic route",
  "status": "active",
  "passengers": []
}
```

## Key Features Demonstrated

### 1. Authentication Flow
- ✅ Welcome screen with clear options
- ✅ Sign up with validation
- ✅ Login with error handling
- ✅ Session management
- ✅ Secure logout

### 2. Trip Management
- ✅ Post trips with detailed information
- ✅ Date/time picker integration
- ✅ Preference selection
- ✅ Form validation
- ✅ Real-time save to Firestore

### 3. Trip Discovery
- ✅ Browse all available trips
- ✅ Search by location
- ✅ Filter by preferences
- ✅ Dynamic trip cards
- ✅ Empty state handling

### 4. User Experience
- ✅ Bottom navigation for easy access
- ✅ Material Design UI
- ✅ Intuitive forms
- ✅ Clear visual hierarchy
- ✅ Responsive layouts

### 5. Profile Management
- ✅ View user information
- ✅ Set gender preferences
- ✅ Configure ride preferences
- ✅ Save to cloud database
- ✅ Persistent storage

## Testing the App

### Manual Testing Checklist

**Authentication:**
- [ ] Sign up with new account
- [ ] Verify email validation
- [ ] Test password mismatch error
- [ ] Login with created account
- [ ] Test invalid credentials error
- [ ] Logout successfully

**Post Trip:**
- [ ] Fill all trip details
- [ ] Select date/time using picker
- [ ] Check preference boxes
- [ ] Submit and verify success
- [ ] Check trip appears in history

**Browse Trips:**
- [ ] View all active trips
- [ ] Search by origin
- [ ] Search by destination
- [ ] Apply preference filters
- [ ] Verify filtering works correctly

**Profile:**
- [ ] View user information
- [ ] Change gender preference
- [ ] Toggle other preferences
- [ ] Save and verify update
- [ ] Check persistence after logout/login

**Navigation:**
- [ ] Test all bottom nav tabs
- [ ] Verify correct screens load
- [ ] Check back button behavior
- [ ] Test logout returns to welcome

## Troubleshooting

### App Won't Build
**Issue**: Gradle sync fails or build errors
**Solution**: 
- Ensure `google-services.json` is in `app/` directory
- Check internet connection
- Invalidate caches: File → Invalidate Caches / Restart
- Clean project: Build → Clean Project

### Firebase Connection Issues
**Issue**: "Firebase not configured" error
**Solution**:
- Verify `google-services.json` package name matches app
- Check Firebase Authentication is enabled
- Ensure Firestore database is created
- Verify Firebase project API keys are active

### Login/Signup Not Working
**Issue**: Authentication fails
**Solution**:
- Check Firebase Authentication is enabled
- Verify email/password method is enabled
- Check internet connection
- Look at Logcat for specific errors

### Trips Not Showing
**Issue**: Browse screen shows "No trips available"
**Solution**:
- Post a trip first from Post Trip tab
- Check Firestore database rules (should be in test mode)
- Verify internet connection
- Check Logcat for Firestore errors

### Date Picker Issues
**Issue**: Date picker doesn't appear
**Solution**:
- This is device-dependent behavior
- Try on different API level emulator
- Check that field is set to focusable="false" and clickable="true"

## Performance Notes

- **Initial Load**: ~2-3 seconds (Firebase initialization)
- **Login**: ~1-2 seconds (network dependent)
- **Trip Posting**: ~1 second (Firestore write)
- **Browse Loading**: ~1-2 seconds (Firestore query)
- **Profile Update**: <1 second (Firestore update)

## Next Steps / Phase 2 Features

While not yet implemented, these features are planned:

1. **Booking System** - Passengers can book trips
2. **Real-time Chat** - In-app messaging
3. **Google Maps** - Map-based trip selection
4. **Notifications** - Push notifications for updates
5. **Ratings** - User rating system
6. **Payment** - Optional payment integration

## Conclusion

This Vehicle Sharing App demonstrates:
- Clean Android architecture with MVVM pattern
- Firebase integration (Auth + Firestore)
- Material Design UI principles
- User-friendly navigation
- Essential trip sharing functionality

The app provides a solid foundation for a vehicle sharing platform and is ready for further enhancement in Phase 2 of development.

---

For more information, see:
- `README.md` - Project overview and setup
- `docs/` - Detailed documentation
- `PROJECT_SUMMARY.md` - Complete project summary
