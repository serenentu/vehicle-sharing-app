# Vehicle Sharing App - Step-by-Step User Journey

This document walks through complete user journeys in the Vehicle Sharing App, showing what happens at each step.

## Table of Contents
1. [Journey 1: New User Registration](#journey-1-new-user-registration)
2. [Journey 2: Existing User Login](#journey-2-existing-user-login)
3. [Journey 3: Posting a Trip](#journey-3-posting-a-trip)
4. [Journey 4: Browsing and Filtering Trips](#journey-4-browsing-and-filtering-trips)
5. [Journey 5: Managing Profile](#journey-5-managing-profile)
6. [Journey 6: Viewing Trip History](#journey-6-viewing-trip-history)
7. [Journey 7: Logout and Re-login](#journey-7-logout-and-re-login)

---

## Journey 1: New User Registration

### Step 1: Launch App
**Action**: User opens the Vehicle Sharing App for the first time  
**Screen**: Welcome Screen  
**What You See**:
- App title and tagline
- "Login" button
- "Sign Up" button

**Expected Outcome**: Clean welcome screen with two clear options

---

### Step 2: Navigate to Sign Up
**Action**: User taps "Sign Up" button  
**Screen**: Sign Up Screen  
**What You See**:
- "Create Account" header
- Four input fields (Full Name, Email, Password, Confirm Password)
- "Sign Up" button
- "Sign up with Google" button
- "Already have an account? Login" link

**Expected Outcome**: Sign up form loads immediately

---

### Step 3: Fill Registration Form
**Action**: User enters registration information  
**Input**:
- Full Name: "John Doe"
- Email: "john.doe@example.com"
- Password: "password123"
- Confirm Password: "password123"

**What You See**: Text appears in each field as typed

**Expected Outcome**: All fields accept input correctly

---

### Step 4: Submit Registration
**Action**: User taps "Sign Up" button  
**What Happens**:
1. Form validates all fields
2. Loading indicator appears briefly
3. Firebase creates authentication account
4. Firestore creates user profile document

**What You See**: 
- Brief loading state
- Transition to main app

**Expected Outcome**: 
- Account successfully created
- User automatically logged in
- Navigated to History screen (Browse Trips tab)
- Bottom navigation now visible

**Firestore Data Created**:
```json
{
  "users/[uid]": {
    "uid": "[auto-generated-id]",
    "fullName": "John Doe",
    "email": "john.doe@example.com",
    "genderPreference": "No Preference",
    "noSmokingPreference": false,
    "noPetsPreference": false,
    "musicPreference": false,
    "rating": 0.0,
    "totalRides": 0
  }
}
```

---

### Error Scenarios in Registration

#### Scenario A: Empty Fields
**Action**: User taps "Sign Up" with empty fields  
**Expected Outcome**: Error message "Please fill in all fields"  
**Screen Behavior**: Stays on Sign Up screen

#### Scenario B: Invalid Email
**Action**: User enters "notanemail" in email field  
**Expected Outcome**: Error message "Invalid email format"  
**Screen Behavior**: Stays on Sign Up screen

#### Scenario C: Short Password
**Action**: User enters "123" as password  
**Expected Outcome**: Error message "Password must be at least 6 characters"  
**Screen Behavior**: Stays on Sign Up screen

#### Scenario D: Password Mismatch
**Action**: User enters different passwords in Password and Confirm Password  
**Expected Outcome**: Error message "Passwords do not match"  
**Screen Behavior**: Stays on Sign Up screen

#### Scenario E: Email Already Exists
**Action**: User tries to register with existing email  
**Expected Outcome**: Firebase error "Email already in use"  
**Screen Behavior**: Stays on Sign Up screen with error message

---

## Journey 2: Existing User Login

### Step 1: Launch App
**Action**: Existing user opens the app  
**Screen**: Welcome Screen  
**What You See**: Same welcome screen as new users

---

### Step 2: Navigate to Login
**Action**: User taps "Login" button  
**Screen**: Login Screen  
**What You See**:
- "Login" header
- Email field
- Password field
- "Login" button
- "Don't have an account? Sign up" link

**Expected Outcome**: Login form loads

---

### Step 3: Enter Credentials
**Action**: User enters login information  
**Input**:
- Email: "john.doe@example.com"
- Password: "password123"

**What You See**: Credentials appear as typed (password masked)

---

### Step 4: Submit Login
**Action**: User taps "Login" button  
**What Happens**:
1. Form validates inputs
2. Loading indicator appears
3. Firebase authenticates credentials
4. App retrieves user session

**Expected Outcome**: 
- Login successful
- Navigated to Browse Trips screen
- Bottom navigation visible
- User session persists until logout

---

### Error Scenarios in Login

#### Scenario A: Wrong Password
**Action**: User enters incorrect password  
**Expected Outcome**: Error "Invalid email or password"  
**Screen Behavior**: Stays on Login screen

#### Scenario B: Non-existent Email
**Action**: User enters email not in system  
**Expected Outcome**: Error "Invalid email or password"  
**Screen Behavior**: Stays on Login screen

#### Scenario C: Empty Fields
**Action**: User taps Login with empty fields  
**Expected Outcome**: Error "Please fill in all fields"  
**Screen Behavior**: Stays on Login screen

---

## Journey 3: Posting a Trip

**Prerequisites**: User must be logged in

### Step 1: Navigate to Post Trip
**Action**: User taps "Post" tab in bottom navigation  
**Screen**: Post Trip Screen  
**What You See**:
- "Post a Trip" header
- Empty form with all fields
- Checkboxes unchecked by default
- "Post Trip" button at bottom

**Expected Outcome**: Clean form ready for input

---

### Step 2: Enter Origin
**Action**: User taps Origin field and types  
**Input**: "San Francisco"  
**What You See**: Text appears in Origin field

---

### Step 3: Enter Destination
**Action**: User taps Destination field and types  
**Input**: "Los Angeles"  
**What You See**: Text appears in Destination field

---

### Step 4: Select Date and Time
**Action**: User taps Date and Time field  
**What Happens**: 
- Android DatePicker dialog opens
- User selects date: November 5, 2025
- Android TimePicker dialog opens
- User selects time: 10:00 AM

**What You See**: 
- Date picker with calendar
- Time picker with hour/minute selection
- Selected date/time appears in field: "Nov 5, 2025 10:00 AM"

**Expected Outcome**: Date and time correctly formatted in field

---

### Step 5: Enter Seats Available
**Action**: User taps Seats field and types  
**Input**: "3"  
**What You See**: Number appears in field

---

### Step 6: Select Preferences
**Action**: User checks preference boxes  
**Selections**:
- ☑ No Smoking
- ☑ No Pets
- ☐ Music Allowed (left unchecked)

**What You See**: Checkmarks appear in selected boxes

---

### Step 7: Add Notes (Optional)
**Action**: User taps Additional Notes field  
**Input**: "Taking Highway 1 scenic route. Great views!"  
**What You See**: Text appears in multiline field

---

### Step 8: Submit Trip
**Action**: User taps "Post Trip" button  
**What Happens**:
1. Form validates all required fields
2. Loading indicator appears
3. Creates trip document in Firestore
4. Success message displays
5. Form clears

**Expected Outcome**: 
- Success toast: "✓ Trip posted successfully!"
- Form resets to empty
- Trip now visible in History tab

**Firestore Data Created**:
```json
{
  "trips/[auto-id]": {
    "tripId": "[auto-generated]",
    "driverUid": "[current-user-uid]",
    "driverName": "John Doe",
    "origin": "San Francisco",
    "destination": "Los Angeles",
    "dateTime": 1699200000000,
    "seatsAvailable": 3,
    "noSmoking": true,
    "noPets": true,
    "musicAllowed": false,
    "additionalNotes": "Taking Highway 1 scenic route. Great views!",
    "status": "active",
    "passengers": []
  }
}
```

---

### Step 9: Verify Posted Trip
**Action**: User switches to History tab  
**Screen**: History Screen  
**What You See**:
- Trip card showing:
  - "● ACTIVE"
  - "San Francisco → Los Angeles"
  - "📅 Nov 5, 2025"
  - "🕐 10:00 AM"
  - "💺 Seats: 3 available"
  - "🚭 No Smoking"
  - "🚫🐕 No Pets"
  - "Note: Taking Highway 1 scenic route..."

**Expected Outcome**: Posted trip appears in history

---

### Error Scenarios in Posting

#### Scenario A: Missing Required Field
**Action**: User submits with empty Origin  
**Expected Outcome**: Error "Please fill in all required fields"  
**Screen Behavior**: Form not submitted

#### Scenario B: Invalid Seats Number
**Action**: User enters "0" or negative number for seats  
**Expected Outcome**: Error "Seats must be at least 1"  
**Screen Behavior**: Form not submitted

#### Scenario C: Date in Past
**Action**: User selects yesterday's date  
**Expected Outcome**: Error "Trip date must be in the future"  
**Screen Behavior**: Form not submitted

---

## Journey 4: Browsing and Filtering Trips

**Prerequisites**: User must be logged in; some trips must exist

### Step 1: Navigate to Browse
**Action**: User taps "Browse" tab (default tab after login)  
**Screen**: Browse Trips Screen  
**What You See**:
- "Browse Available Trips" header
- Search fields (From/To)
- "Show Filters" button
- List of all active trip cards

**Expected Outcome**: All active trips from all users displayed

---

### Step 2: View Trip Details
**Action**: User scrolls through trip list  
**What You See**: Multiple trip cards with:
- Origin → Destination
- Date and time
- Driver name
- Seats available
- Preference icons

**Example Trip Cards**:
```
┌─────────────────────────────────┐
│ San Francisco → Los Angeles     │
│ Nov 5, 2025 • 10:00 AM          │
│ 👤 John Doe                     │
│ 💺 3 seats available            │
│ 🚭 No Smoking  🚫🐕 No Pets    │
└─────────────────────────────────┘

┌─────────────────────────────────┐
│ Seattle → Portland              │
│ Nov 6, 2025 • 2:00 PM           │
│ 👤 Jane Smith                   │
│ 💺 2 seats available            │
│ 🎵 Music Allowed                │
└─────────────────────────────────┘
```

**Expected Outcome**: All active trips visible and scrollable

---

### Step 3: Search by Location
**Action**: User types in search fields  
**Input**:
- From: "San Francisco"
- To: "Los Angeles"

**What Happens**: 
- List filters in real-time as user types
- Only trips matching origin/destination show

**What You See**: Only San Francisco → Los Angeles trips displayed

**Expected Outcome**: Filtered list shows relevant trips only

---

### Step 4: Clear Search
**Action**: User clears the search fields  
**What Happens**: All trips reappear

**Expected Outcome**: Full trip list restored

---

### Step 5: Open Filters
**Action**: User taps "Show Filters" button  
**What Happens**: Filter panel expands below button  
**What You See**:
- "Preferences" section
- Three checkboxes:
  - ☐ No Smoking
  - ☐ No Pets
  - ☐ Music Allowed
- "Apply Filters" button

**Expected Outcome**: Filter options visible

---

### Step 6: Select Filter Options
**Action**: User checks filter boxes  
**Selections**:
- ☑ No Smoking
- ☐ No Pets
- ☑ Music Allowed

**What You See**: Checkmarks in selected boxes

---

### Step 7: Apply Filters
**Action**: User taps "Apply Filters" button  
**What Happens**:
- Filter panel stays open
- Trip list filters immediately
- Only trips matching ALL selected criteria show

**What You See**: 
- Reduced list of trips
- All visible trips have both "No Smoking" AND "Music Allowed"

**Expected Outcome**: Only matching trips displayed

---

### Step 8: No Matches Scenario
**Action**: User selects very specific filters  
**Selections**:
- ☑ No Smoking
- ☑ No Pets
- ☑ Music Allowed

**What Happens**: If no trips match all criteria  
**What You See**:
```
    No trips available.
    Try adjusting your filters.
```

**Expected Outcome**: Empty state message displayed

---

### Step 9: Close Filters
**Action**: User taps "Hide Filters" button  
**What Happens**: Filter panel collapses  
**What You See**: 
- Filters hidden
- Filter selections persist
- Filtered trip list still shows

**Expected Outcome**: More screen space for trip list

---

## Journey 5: Managing Profile

**Prerequisites**: User must be logged in

### Step 1: Navigate to Profile
**Action**: User taps "Profile" tab in bottom navigation  
**Screen**: Profile Screen  
**What You See**:
- "Profile" header
- User icon placeholder
- User's full name: "John Doe"
- User's email: "john.doe@example.com"
- Gender preference radio buttons
- Other preference checkboxes
- "Save Preferences" button
- "Logout" button

**Expected Outcome**: Profile screen loads with current user data

---

### Step 2: View Current Preferences
**Action**: User reviews current settings  
**What You See** (initial state):
- ⦿ No Preference (selected)
- ○ Same Gender Only
- ☐ Prefer No Smoking
- ☐ Prefer No Pets
- ☐ Enjoy Music During Ride

**Expected Outcome**: Default preferences shown

---

### Step 3: Change Gender Preference
**Action**: User taps "Same Gender Only" radio button  
**What Happens**: Selection changes  
**What You See**:
- ○ No Preference
- ⦿ Same Gender Only (now selected)

**Expected Outcome**: Radio button selection updated

---

### Step 4: Update Other Preferences
**Action**: User checks preference boxes  
**Selections**:
- ☑ Prefer No Smoking
- ☐ Prefer No Pets
- ☑ Enjoy Music During Ride

**What You See**: Checkmarks appear in selected boxes

---

### Step 5: Save Preferences
**Action**: User taps "Save Preferences" button  
**What Happens**:
1. Loading indicator briefly appears
2. Firestore updates user document
3. Success toast displays

**What You See**: 
```
  ┌────────────────────────┐
  │ ✓ Preferences saved!   │
  └────────────────────────┘
```

**Expected Outcome**: 
- Preferences saved to database
- Success confirmation shown
- User remains on Profile screen

**Firestore Data Updated**:
```json
{
  "users/[uid]": {
    "genderPreference": "Same Gender Only",
    "noSmokingPreference": true,
    "noPetsPreference": false,
    "musicPreference": true
  }
}
```

---

### Step 6: Verify Persistence
**Action**: User navigates away and returns to Profile  
**What You See**: Previously saved preferences still selected

**Expected Outcome**: Preferences persist across navigation

---

## Journey 6: Viewing Trip History

**Prerequisites**: User must be logged in and have posted trips

### Step 1: Navigate to History
**Action**: User taps "History" tab  
**Screen**: History Screen  
**What You See**:
- "My Trips" header
- List of user's posted trips
- Each trip shows status, route, date, time, seats, preferences

**Expected Outcome**: All user's trips displayed

---

### Step 2: View Active Trips
**Action**: User scrolls through history  
**What You See** (for active trips):
```
┌─────────────────────────────────┐
│ ● ACTIVE                        │
│                                 │
│ San Francisco → Los Angeles     │
│                                 │
│ 📅 Nov 5, 2025                  │
│ 🕐 10:00 AM                     │
│                                 │
│ 💺 Seats: 3 available           │
│ 🚭 No Smoking                   │
│ 🚫🐕 No Pets                    │
│                                 │
│ Note: Taking Highway 1...       │
└─────────────────────────────────┘
```

**Expected Outcome**: Active trips shown with green indicator

---

### Step 3: View Completed Trips
**Action**: User continues scrolling  
**What You See** (for completed trips):
```
┌─────────────────────────────────┐
│ ○ COMPLETED                     │
│                                 │
│ Oakland → Berkeley              │
│                                 │
│ 📅 Nov 1, 2025                  │
│ 🕐 9:00 AM                      │
│                                 │
│ 💺 Seats: 1 was available       │
└─────────────────────────────────┘
```

**Expected Outcome**: Completed trips shown with gray indicator

---

### Step 4: Empty History State
**Scenario**: New user with no posted trips  
**Action**: New user navigates to History  
**What You See**:
```
        📋
        
  No trips posted yet
  
  Go to the Post Trip tab to
    create your first trip!
```

**Expected Outcome**: Friendly empty state message

---

### Step 5: Trip Ordering
**What You See**: Trips ordered by:
1. Active trips first
2. Completed trips second
3. Within each group: newest to oldest

**Expected Outcome**: Logical, chronological ordering

---

## Journey 7: Logout and Re-login

**Prerequisites**: User must be logged in

### Step 1: Navigate to Profile
**Action**: User taps "Profile" tab  
**Screen**: Profile Screen  
**What You See**: Profile screen with "Logout" button at bottom

---

### Step 2: Initiate Logout
**Action**: User taps "Logout" button  
**What Happens**:
1. Firebase signs out user
2. Session cleared
3. App navigates to Welcome screen
4. Bottom navigation disappears

**What You See**: 
- Brief transition
- Welcome screen appears
- Bottom navigation gone

**Expected Outcome**: 
- User logged out successfully
- Returned to Welcome screen
- All session data cleared

---

### Step 3: Verify Logout
**Action**: User attempts to use Android back button  
**Expected Outcome**: 
- Cannot return to authenticated screens
- Back button exits app from Welcome screen

---

### Step 4: Re-login
**Action**: User taps "Login" button on Welcome screen  
**Input**: Enter same credentials as before  
**Expected Outcome**: 
- Login successful
- Returns to Browse screen
- Bottom navigation visible
- Previous preferences still saved

---

### Step 5: Verify Data Persistence
**Action**: User navigates to Profile tab  
**What You See**: 
- Previously saved preferences still selected
- User data unchanged

**Expected Outcome**: 
- User profile persisted in Firestore
- Preferences unchanged after logout/login

---

### Step 6: Check Posted Trips
**Action**: User navigates to History tab  
**What You See**: All previously posted trips still visible

**Expected Outcome**: 
- Trip data persisted in Firestore
- User's trips unchanged

---

## Complete User Flow Diagram

```
START
  │
  ├─→ [First Time User]
  │   ├─→ Welcome Screen
  │   ├─→ Sign Up Screen
  │   ├─→ Create Account
  │   └─→ Main App
  │
  └─→ [Returning User]
      ├─→ Welcome Screen
      ├─→ Login Screen
      ├─→ Authenticate
      └─→ Main App
          │
          ├─→ Browse Trips Tab
          │   ├─→ View all trips
          │   ├─→ Search trips
          │   └─→ Filter trips
          │
          ├─→ Post Trip Tab
          │   ├─→ Fill form
          │   ├─→ Select date/time
          │   ├─→ Post trip
          │   └─→ View in History
          │
          ├─→ History Tab
          │   ├─→ View posted trips
          │   └─→ Check status
          │
          └─→ Profile Tab
              ├─→ Update preferences
              ├─→ Save changes
              └─→ Logout
                  └─→ Welcome Screen
```

---

## Key Observations

### What Works Well

1. **Authentication Flow**:
   - Clear welcome screen
   - Easy signup process
   - Simple login
   - Secure session management

2. **Trip Management**:
   - Intuitive trip posting
   - Comprehensive trip details
   - Real-time updates
   - Persistent storage

3. **Discovery**:
   - Easy browsing
   - Effective searching
   - Useful filtering
   - Clear trip information

4. **User Experience**:
   - Bottom navigation always accessible
   - Consistent UI patterns
   - Clear feedback messages
   - Logical information hierarchy

### Areas for Future Enhancement

1. **Trip Booking**: 
   - Currently view-only for passengers
   - Future: Add booking system

2. **Maps Integration**:
   - Text-based locations
   - Future: Google Maps integration

3. **Communication**:
   - No in-app chat
   - Future: Messaging system

4. **Notifications**:
   - No push notifications
   - Future: Trip updates and reminders

5. **Ratings**:
   - Rating field exists but unused
   - Future: User rating system

---

## Testing Checklist

Use this checklist to verify all functionality:

### Authentication
- [ ] Sign up creates account
- [ ] Sign up validates fields
- [ ] Login authenticates user
- [ ] Login shows errors for invalid credentials
- [ ] Session persists until logout
- [ ] Logout clears session

### Trip Posting
- [ ] All fields accept input
- [ ] Date/time picker works
- [ ] Form validates required fields
- [ ] Trip saves to Firestore
- [ ] Success message appears
- [ ] Form clears after post

### Trip Browsing
- [ ] All active trips load
- [ ] Search by origin works
- [ ] Search by destination works
- [ ] Filters apply correctly
- [ ] Multiple filters combine properly
- [ ] Empty state shows when no matches

### History
- [ ] User's trips display
- [ ] Active trips show correctly
- [ ] Completed trips show correctly
- [ ] Trips ordered properly
- [ ] Empty state works for new users

### Profile
- [ ] User info displays correctly
- [ ] Preferences can be changed
- [ ] Save updates Firestore
- [ ] Success message appears
- [ ] Preferences persist

### Navigation
- [ ] Bottom nav switches screens
- [ ] Active tab highlighted
- [ ] Back button behaves correctly
- [ ] Bottom nav hidden when logged out

---

## Conclusion

This step-by-step guide demonstrates the complete functionality of the Vehicle Sharing App. The app successfully implements:

- ✅ Complete authentication system
- ✅ Trip posting with full details
- ✅ Trip browsing and discovery
- ✅ Search and filtering
- ✅ Trip history tracking
- ✅ User profile management
- ✅ Preference persistence
- ✅ Firebase integration
- ✅ Intuitive navigation

The app provides a solid foundation for vehicle sharing functionality and demonstrates proficiency in Android development with Kotlin and Firebase.
