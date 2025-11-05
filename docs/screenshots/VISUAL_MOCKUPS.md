# Vehicle Sharing App - Visual Mockups

This document provides detailed visual mockups of each screen in the Vehicle Sharing App.

## Table of Contents
1. [Welcome Screen](#1-welcome-screen)
2. [Sign Up Screen](#2-sign-up-screen)
3. [Login Screen](#3-login-screen)
4. [Browse Trips Screen](#4-browse-trips-screen)
5. [Post Trip Screen](#5-post-trip-screen)
6. [History Screen](#6-history-screen)
7. [Profile Screen](#7-profile-screen)

---

## 1. Welcome Screen

### Layout Description
- **Type**: Entry screen, first screen users see
- **Background**: Clean white background
- **Alignment**: Centered content
- **Navigation**: No bottom navigation (not logged in)

### Visual Mockup

```
╔═══════════════════════════════════════╗
║                                       ║
║                                       ║
║                                       ║
║         🚗  🚙  🚕                    ║
║                                       ║
║      Vehicle Sharing App              ║
║      ═══════════════════              ║
║                                       ║
║                                       ║
║   Share rides, save money,            ║
║      reduce emissions                 ║
║                                       ║
║                                       ║
║                                       ║
║      ┌─────────────────────┐         ║
║      │       LOGIN         │         ║
║      └─────────────────────┘         ║
║                                       ║
║      ┌─────────────────────┐         ║
║      │      SIGN UP        │         ║
║      └─────────────────────┘         ║
║                                       ║
║                                       ║
║                                       ║
║                                       ║
║                                       ║
╚═══════════════════════════════════════╝
```

### Elements

**Title**: "Vehicle Sharing App"
- Font size: 28sp
- Style: Bold
- Color: Primary color (Material Blue)

**Tagline**: "Share rides, save money, reduce emissions"
- Font size: 16sp
- Style: Regular
- Color: Dark gray
- Centered text

**Login Button**:
- Full width (with padding)
- Primary color background
- White text
- Rounded corners

**Sign Up Button**:
- Full width (with padding)
- Outlined style or secondary color
- Primary color text
- Rounded corners

### User Actions
1. Tap "LOGIN" → Navigate to Login Screen
2. Tap "SIGN UP" → Navigate to Sign Up Screen

---

## 2. Sign Up Screen

### Layout Description
- **Type**: Scrollable form
- **Background**: White
- **Validation**: Real-time field validation
- **Navigation**: No bottom navigation (not logged in)

### Visual Mockup

```
╔═══════════════════════════════════════╗
║  ←                                    ║
║                                       ║
║        Create Account                 ║
║        ─────────────                  ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ Full Name                       │ ║
║  │ ___________________________     │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ Email                           │ ║
║  │ ___________________________     │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ Password                        │ ║
║  │ ●●●●●●●●●●●●                   │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ Confirm Password                │ ║
║  │ ●●●●●●●●●●●●                   │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
║      ┌─────────────────────┐         ║
║      │      SIGN UP        │         ║
║      └─────────────────────┘         ║
║                                       ║
║              OR                       ║
║                                       ║
║      ┌─────────────────────┐         ║
║      │ 🅖  Sign up with    │         ║
║      │     Google          │         ║
║      └─────────────────────┘         ║
║                                       ║
║   Already have an account?            ║
║           Login                       ║
║                                       ║
╚═══════════════════════════════════════╝
```

### Elements

**Form Fields**:
1. **Full Name**
   - Hint: "Full Name"
   - Input type: Text
   - Validation: Not empty

2. **Email**
   - Hint: "Email"
   - Input type: Email address
   - Validation: Valid email format

3. **Password**
   - Hint: "Password"
   - Input type: Password (masked)
   - Validation: Minimum 6 characters

4. **Confirm Password**
   - Hint: "Confirm Password"
   - Input type: Password (masked)
   - Validation: Matches password field

**Sign Up Button**:
- Validates all fields
- Creates Firebase Auth account
- Creates Firestore user profile
- Navigates to main app on success

**Google Sign Up Button**:
- Shows Google icon
- Currently placeholder
- Future: Google OAuth integration

**Login Link**:
- Clickable text
- Navigates to Login Screen

### Validation Messages
- "Please enter your full name"
- "Invalid email format"
- "Password must be at least 6 characters"
- "Passwords do not match"
- "Email already in use"

---

## 3. Login Screen

### Layout Description
- **Type**: Simple form
- **Background**: White
- **Alignment**: Centered
- **Navigation**: No bottom navigation (not logged in)

### Visual Mockup

```
╔═══════════════════════════════════════╗
║  ←                                    ║
║                                       ║
║                                       ║
║                                       ║
║            Login                      ║
║            ─────                      ║
║                                       ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ Email                           │ ║
║  │ ___________________________     │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ Password                        │ ║
║  │ ●●●●●●●●●●●●                   │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
║                                       ║
║      ┌─────────────────────┐         ║
║      │       LOGIN         │         ║
║      └─────────────────────┘         ║
║                                       ║
║                                       ║
║   Don't have an account?              ║
║          Sign up                      ║
║                                       ║
║                                       ║
║                                       ║
║                                       ║
╚═══════════════════════════════════════╝
```

### Elements

**Form Fields**:
1. **Email**
   - Hint: "Email"
   - Input type: Email address
   - Pre-filled if returning user

2. **Password**
   - Hint: "Password"
   - Input type: Password (masked)

**Login Button**:
- Authenticates with Firebase
- Validates credentials
- Shows loading indicator during auth
- Navigates to main app on success

**Sign Up Link**:
- Clickable text
- Navigates to Sign Up Screen

### Success Flow
1. User enters credentials
2. Taps "LOGIN"
3. Firebase validates
4. User redirected to Browse Trips screen
5. Bottom navigation appears

### Error Messages
- "Invalid email or password"
- "Please fill in all fields"
- "Network error, please try again"

---

## 4. Browse Trips Screen

### Layout Description
- **Type**: Main screen, list view
- **Background**: Light gray
- **Scroll**: Vertical
- **Navigation**: Bottom navigation visible (Tab 1)

### Visual Mockup

```
╔═══════════════════════════════════════╗
║  Browse Available Trips               ║
║                                       ║
║  ┌──────────┐    ┌──────────┐        ║
║  │   From   │    │    To    │        ║
║  └──────────┘    └──────────┘        ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │      Show Filters ▼            │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ San Francisco → Los Angeles     │ ║
║  │ ──────────────────────────────  │ ║
║  │ Nov 5, 2025 • 10:00 AM          │ ║
║  │ 👤 John Doe                     │ ║
║  │ 💺 3 seats available            │ ║
║  │ 🚭 No Smoking  🎵 Music OK      │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ Seattle → Portland              │ ║
║  │ ──────────────────────────────  │ ║
║  │ Nov 6, 2025 • 2:00 PM           │ ║
║  │ 👤 Jane Smith                   │ ║
║  │ 💺 2 seats available            │ ║
║  │ 🚭 No Smoking  🚫🐕 No Pets    │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ Oakland → Berkeley              │ ║
║  │ ──────────────────────────────  │ ║
║  │ Nov 7, 2025 • 9:00 AM           │ ║
║  │ 👤 Mike Johnson                 │ ║
║  │ 💺 1 seat available             │ ║
║  │ 🎵 Music OK                     │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
╠═══════════════════════════════════════╣
║   🔍      ➕       📋       👤       ║
║  Browse  Post    History  Profile    ║
╚═══════════════════════════════════════╝
```

### With Filters Expanded

```
╔═══════════════════════════════════════╗
║  Browse Available Trips               ║
║                                       ║
║  ┌──────────┐    ┌──────────┐        ║
║  │   From   │    │    To    │        ║
║  └──────────┘    └──────────┘        ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │      Hide Filters ▲            │ ║
║  └─────────────────────────────────┘ ║
║  ╔═════════════════════════════════╗ ║
║  ║   Preferences                   ║ ║
║  ║   ☑ No Smoking                  ║ ║
║  ║   ☐ No Pets                     ║ ║
║  ║   ☑ Music Allowed               ║ ║
║  ║                                 ║ ║
║  ║   ┌────────────────────┐        ║ ║
║  ║   │  Apply Filters     │        ║ ║
║  ║   └────────────────────┘        ║ ║
║  ╚═════════════════════════════════╝ ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ San Francisco → Los Angeles     │ ║
║  │ ──────────────────────────────  │ ║
║  │ Nov 5, 2025 • 10:00 AM          │ ║
║  │ 👤 John Doe                     │ ║
║  │ 💺 3 seats available            │ ║
║  │ 🚭 No Smoking  🎵 Music OK      │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
╠═══════════════════════════════════════╣
║   🔍      ➕       📋       👤       ║
║  Browse  Post    History  Profile    ║
╚═══════════════════════════════════════╝
```

### Elements

**Search Bar**:
- Two input fields side by side
- "From" (origin)
- "To" (destination)
- Real-time filtering as user types

**Filter Toggle Button**:
- Shows/hides filter panel
- Arrow indicator (▼ when collapsed, ▲ when expanded)

**Filter Panel** (expandable):
- Checkbox: No Smoking
- Checkbox: No Pets
- Checkbox: Music Allowed
- Apply Filters button

**Trip Cards**:
Each card displays:
- Origin → Destination (bold)
- Date and time
- Driver name with icon
- Seats available with icon
- Preference icons:
  - 🚭 No Smoking
  - 🚫🐕 No Pets
  - 🎵 Music Allowed

**Empty State** (when no trips):
```
    No trips available.
    Try adjusting your filters
    or check back later.
```

**Bottom Navigation**:
- Tab 1 (Browse) - Active/highlighted
- Tab 2 (Post)
- Tab 3 (History)
- Tab 4 (Profile)

---

## 5. Post Trip Screen

### Layout Description
- **Type**: Scrollable form
- **Background**: White
- **Validation**: Required fields
- **Navigation**: Bottom navigation visible (Tab 2)

### Visual Mockup

```
╔═══════════════════════════════════════╗
║  Post a Trip                          ║
║                                       ║
║  Trip Details                         ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ 📍 Origin                       │ ║
║  │ ___________________________     │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ 📍 Destination                  │ ║
║  │ ___________________________     │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ 🕐 Date and Time                │ ║
║  │ Nov 5, 2025 10:00 AM        📅 │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ Seats Available                 │ ║
║  │ ___________________________     │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
║  Preferences                          ║
║  ☑ No Smoking                        ║
║  ☑ No Pets                           ║
║  ☐ Music Allowed                     ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ Additional Notes                │ ║
║  │                                 │ ║
║  │                                 │ ║
║  │                                 │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
║      ┌─────────────────────┐         ║
║      │     POST TRIP       │         ║
║      └─────────────────────┘         ║
║                                       ║
╠═══════════════════════════════════════╣
║   🔍      ➕       📋       👤       ║
║  Browse  Post    History  Profile    ║
╚═══════════════════════════════════════╝
```

### Date/Time Picker Example

When user taps the Date/Time field:

```
╔═══════════════════════════════════════╗
║   Select Date                         ║
║                                       ║
║        November 2025                  ║
║   ◄                           ►       ║
║                                       ║
║   S   M   T   W   T   F   S          ║
║                       1   2   3       ║
║   4   5   6   7   8   9  10          ║
║  11  12  13  14 [15] 16  17          ║
║  18  19  20  21  22  23  24          ║
║  25  26  27  28  29  30               ║
║                                       ║
║        Select Time                    ║
║                                       ║
║           10 : 00  AM                 ║
║                                       ║
║    ┌────────┐    ┌────────┐          ║
║    │ Cancel │    │   OK   │          ║
║    └────────┘    └────────┘          ║
╚═══════════════════════════════════════╝
```

### Elements

**Form Fields**:

1. **Origin**
   - Text input
   - Location icon prefix
   - Placeholder: "Origin"
   - Required field

2. **Destination**
   - Text input
   - Location icon prefix
   - Placeholder: "Destination"
   - Required field

3. **Date and Time**
   - Read-only field (clickable)
   - Calendar icon suffix
   - Opens Android date/time picker
   - Format: "MMM d, yyyy h:mm a"
   - Required field

4. **Seats Available**
   - Numeric input
   - Accepts numbers 1-8
   - Required field

5. **Preferences** (Checkboxes):
   - ☑ No Smoking
   - ☑ No Pets
   - ☐ Music Allowed

6. **Additional Notes**
   - Multi-line text input
   - Height: ~120dp
   - Optional field
   - Max: 500 characters

**Post Trip Button**:
- Full width
- Primary color
- Validates all required fields
- Saves to Firestore
- Shows success message
- Clears form after successful post

### Validation Rules
- Origin: Not empty
- Destination: Not empty
- Date/Time: Selected and in future
- Seats: Between 1 and 8
- Notes: Max 500 characters

### Success Message
```
✓ Trip posted successfully!
```

---

## 6. History Screen

### Layout Description
- **Type**: Scrollable list
- **Background**: Light gray
- **Content**: User's posted trips
- **Navigation**: Bottom navigation visible (Tab 3)

### Visual Mockup

```
╔═══════════════════════════════════════╗
║  My Trips                             ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ ● ACTIVE                        │ ║
║  │                                 │ ║
║  │ San Francisco → Los Angeles     │ ║
║  │                                 │ ║
║  │ 📅 Nov 5, 2025                  │ ║
║  │ 🕐 10:00 AM                     │ ║
║  │                                 │ ║
║  │ 💺 Seats: 3 available           │ ║
║  │ 🚭 No Smoking                   │ ║
║  │ 🎵 Music Allowed                │ ║
║  │                                 │ ║
║  │ Note: Highway 1 scenic route    │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ ● ACTIVE                        │ ║
║  │                                 │ ║
║  │ Seattle → Portland              │ ║
║  │                                 │ ║
║  │ 📅 Nov 6, 2025                  │ ║
║  │ 🕐 2:00 PM                      │ ║
║  │                                 │ ║
║  │ 💺 Seats: 2 available           │ ║
║  │ 🚭 No Smoking                   │ ║
║  │ 🚫🐕 No Pets                    │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
║  ┌─────────────────────────────────┐ ║
║  │ ○ COMPLETED                     │ ║
║  │                                 │ ║
║  │ Oakland → Berkeley              │ ║
║  │                                 │ ║
║  │ 📅 Nov 1, 2025                  │ ║
║  │ 🕐 9:00 AM                      │ ║
║  │                                 │ ║
║  │ 💺 Seats: 1 was available       │ ║
║  └─────────────────────────────────┘ ║
║                                       ║
╠═══════════════════════════════════════╣
║   🔍      ➕       📋       👤       ║
║  Browse  Post    History  Profile    ║
╚═══════════════════════════════════════╝
```

### Empty State

```
╔═══════════════════════════════════════╗
║  My Trips                             ║
║                                       ║
║                                       ║
║                                       ║
║                                       ║
║            📋                         ║
║                                       ║
║      No trips posted yet              ║
║                                       ║
║   Go to the Post Trip tab to          ║
║      create your first trip!          ║
║                                       ║
║                                       ║
║                                       ║
║                                       ║
║                                       ║
╠═══════════════════════════════════════╣
║   🔍      ➕       📋       👤       ║
║  Browse  Post    History  Profile    ║
╚═══════════════════════════════════════╝
```

### Elements

**Trip Cards**:

Each card shows:

1. **Status Indicator**:
   - ● ACTIVE (green)
   - ○ COMPLETED (gray)

2. **Trip Route**:
   - Origin → Destination
   - Bold, larger text

3. **Date and Time**:
   - Calendar icon + date
   - Clock icon + time

4. **Seats Information**:
   - Seat icon + count
   - "available" for active
   - "was available" for completed

5. **Preferences**:
   - Icons with text labels
   - Only shows enabled preferences

6. **Additional Notes**:
   - Shown if provided
   - Truncated if too long
   - Prefix: "Note:"

**Ordering**:
- Active trips first
- Then completed trips
- Within each group: newest first

**No Trips Message**:
- Centered in screen
- Friendly icon
- Helpful message
- Link to Post Trip tab

---

## 7. Profile Screen

### Layout Description
- **Type**: Scrollable form
- **Background**: White
- **Content**: User info + preferences
- **Navigation**: Bottom navigation visible (Tab 4)

### Visual Mockup

```
╔═══════════════════════════════════════╗
║  Profile                              ║
║                                       ║
║       ┌─────┐                         ║
║       │ 👤  │                         ║
║       └─────┘                         ║
║                                       ║
║      John Doe                         ║
║   john.doe@example.com                ║
║                                       ║
║   ─────────────────────────           ║
║                                       ║
║  Preferences                          ║
║                                       ║
║  Gender Preference                    ║
║  ⦿ No Preference                     ║
║  ○ Same Gender Only                  ║
║                                       ║
║  Other Preferences                    ║
║  ☑ Prefer No Smoking                 ║
║  ☐ Prefer No Pets                    ║
║  ☑ Enjoy Music During Ride           ║
║                                       ║
║      ┌─────────────────────┐         ║
║      │  Save Preferences   │         ║
║      └─────────────────────┘         ║
║                                       ║
║      ┌─────────────────────┐         ║
║      │      Logout         │         ║
║      └─────────────────────┘         ║
║                                       ║
╠═══════════════════════════════════════╣
║   🔍      ➕       📋       👤       ║
║  Browse  Post    History  Profile    ║
╚═══════════════════════════════════════╝
```

### Elements

**User Information** (Read-only):

1. **Profile Icon**:
   - Simple user icon placeholder
   - Future: Could show profile picture

2. **Full Name**:
   - Displayed prominently
   - Font size: 18sp
   - Style: Semi-bold

3. **Email Address**:
   - Displayed below name
   - Font size: 14sp
   - Style: Regular
   - Color: Gray

**Preferences Section**:

1. **Gender Preference** (Radio buttons):
   - Option 1: ⦿ No Preference
   - Option 2: ○ Same Gender Only
   - Only one can be selected

2. **Other Preferences** (Checkboxes):
   - ☑ Prefer No Smoking
   - ☐ Prefer No Pets
   - ☑ Enjoy Music During Ride
   - Multiple can be selected

**Action Buttons**:

1. **Save Preferences**:
   - Primary button
   - Updates Firestore user document
   - Shows success toast
   - Remains on screen

2. **Logout**:
   - Secondary or outlined button
   - Red or warning color
   - Signs out from Firebase
   - Returns to Welcome screen
   - Hides bottom navigation

### Success Toast

After saving:
```
  ┌────────────────────────┐
  │ ✓ Preferences saved!   │
  └────────────────────────┘
```

### Logout Confirmation

Optional (not currently implemented):
```
╔═══════════════════════════════════════╗
║                                       ║
║      Are you sure you want            ║
║          to logout?                   ║
║                                       ║
║    ┌────────┐    ┌────────┐          ║
║    │ Cancel │    │ Logout │          ║
║    └────────┘    └────────┘          ║
║                                       ║
╚═══════════════════════════════════════╝
```

---

## Navigation Patterns

### Bottom Navigation States

**All tabs inactive** (one must be active):
```
┌───────────────────────────────────────┐
│   🔍      ➕       📋       👤       │
│  Browse  Post    History  Profile    │
└───────────────────────────────────────┘
```

**Browse tab active**:
```
┌───────────────────────────────────────┐
│   🔍      ➕       📋       👤       │
│  Browse  Post    History  Profile    │
│   ═══                                 │
└───────────────────────────────────────┘
```

**Post tab active**:
```
┌───────────────────────────────────────┐
│   🔍      ➕       📋       👤       │
│  Browse  Post    History  Profile    │
│          ═══                          │
└───────────────────────────────────────┘
```

### Back Button Behavior

- **Welcome/Login/Signup**: Exit app
- **Browse/Post/History/Profile**: Stay on current screen (don't exit)
- **Within forms**: Standard Android back behavior

### Loading States

When loading data:
```
    ┌────────┐
    │   ⟳    │  Loading...
    └────────┘
```

When saving:
```
    ┌────────┐
    │   ⟳    │  Saving...
    └────────┘
```

---

## Color Scheme

**Primary Colors**:
- Primary: Material Blue (#2196F3)
- Primary Dark: Dark Blue (#1976D2)
- Accent: Light Blue (#03A9F4)

**Status Colors**:
- Success: Green (#4CAF50)
- Error: Red (#F44336)
- Warning: Orange (#FF9800)
- Info: Blue (#2196F3)

**Background**:
- Primary Background: White (#FFFFFF)
- Secondary Background: Light Gray (#F5F5F5)
- Card Background: White with elevation

**Text**:
- Primary Text: Dark Gray (#212121)
- Secondary Text: Medium Gray (#757575)
- Hint Text: Light Gray (#BDBDBD)

---

## Typography

**Headers**:
- Size: 24sp
- Weight: Bold
- Color: Primary Text

**Body Text**:
- Size: 16sp
- Weight: Regular
- Color: Primary Text

**Secondary Text**:
- Size: 14sp
- Weight: Regular
- Color: Secondary Text

**Button Text**:
- Size: 14sp
- Weight: Medium
- Color: White (on primary buttons)

---

## Icons Used

**Navigation**:
- 🔍 Browse (Search icon)
- ➕ Post Trip (Add icon)
- 📋 History (List icon)
- 👤 Profile (Person icon)

**Trip Details**:
- 📍 Location (pin icon)
- 🕐 Time (clock icon)
- 📅 Date (calendar icon)
- 💺 Seats (seat icon)

**Preferences**:
- 🚭 No Smoking
- 🚫🐕 No Pets (or 🐕‍🦺)
- 🎵 Music Allowed

---

## Summary

These visual mockups represent the complete user interface of the Vehicle Sharing App. The design follows Material Design principles with:

- **Clean layouts** with proper spacing
- **Intuitive navigation** with bottom tabs
- **Clear visual hierarchy** with proper typography
- **Consistent styling** across all screens
- **User-friendly forms** with validation
- **Helpful feedback** messages and states

The app provides a professional, polished experience for vehicle sharing with all essential features implemented and working.
