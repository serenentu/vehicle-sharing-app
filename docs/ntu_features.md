# NTU-Centric Features Documentation

## Overview

This document describes the NTU-specific features implemented in the Vehicle Sharing App to cater specifically to NTU students and enhance campus-based carpooling.

## 1. Campus-Aware Ride Matching

### NTU Location Database (`NTULocations.kt`)

The app includes a comprehensive database of NTU-specific locations:

#### Campus Locations (50+ entries)
- **Halls**: Hall 1-16, Pioneer Hall, Crescent Hall, Tamarind Hall
- **Academic Buildings**: 
  - North Spine, South Spine
  - The Hive (Learning Hub)
  - Lee Kong Chian School of Medicine (LKC)
  - School of Art, Design and Media (ADM)
  - Various school buildings (EEE, SCSE, MAE, CEE)
- **Lecture Theatres**: LT1, LT2, LT19-27
- **Dining**: Canteens (A, 2, 9, 11, 13, 14, 16), Food Courts, Cafes
- **Facilities**: Sports Centre, Innovation Centre, Campus Centre

#### Singapore-Wide Destinations
- **East**: Tampines, Pasir Ris, Bedok, Changi Airport, East Coast Park
- **Central**: Orchard Road, Marina Bay, City Hall, Raffles Place, Bugis
- **West**: Jurong East, Jurong West, Clementi, Boon Lay
- **North**: Woodlands, Yishun, Sembawang, Ang Mo Kio
- **Northeast**: Serangoon, Hougang, Punggol, Sengkang

### Location Autocomplete

**Implementation**: `PostTripFragment.kt`

Users benefit from autocomplete suggestions when entering origin and destination:
```kotlin
val locationAdapter = ArrayAdapter(
    requireContext(),
    android.R.layout.simple_dropdown_item_1line,
    NTULocations.ALL_LOCATIONS
)
etOrigin.setAdapter(locationAdapter)
etDestination.setAdapter(locationAdapter)
```

**Benefits**:
- Faster trip posting with familiar locations
- Reduced typos and inconsistent naming
- Easy discovery of popular destinations

### Common Route Suggestions

**Implementation**: Time-based intelligent route suggestions

The app suggests popular routes based on:
- **Day of week** (Friday, Saturday, Sunday, Weekdays)
- **Time of day** (Morning, Evening, Late night)

**Examples**:
- Friday 6pm: "NTU → Tampines" (East side)
- Saturday: "NTU → Orchard" (Shopping)
- Sunday evening: "Tampines → NTU" (Return trips)
- Weekday morning: "NTU → Marina Bay" (CBD)

**Code**:
```kotlin
private fun updateCommonRoutes(textView: TextView) {
    val calendar = Calendar.getInstance()
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    
    // Determine relevant routes based on time
    val relevantRoutes = NTULocations.COMMON_ROUTES.filter { route ->
        route.suggestedTimes.any { time ->
            time.contains(timePeriod, ignoreCase = true)
        }
    }
}
```

## 2. Social Trust & Familiarity

### NTU-Only Access

**Implementation**: `SignupFragment.kt`

Email validation ensures only NTU students can register:
```kotlin
if (!email.endsWith("@e.ntu.edu.sg")) {
    Toast.makeText(
        context,
        "Please use your NTU student email (@e.ntu.edu.sg)",
        Toast.LENGTH_LONG
    ).show()
    return@setOnClickListener
}
```

**Benefits**:
- Verified student community
- Enhanced safety and trust
- Campus-specific features relevance

### Profile Badges

**Data Model** (`User.kt`):
```kotlin
data class User(
    // ... existing fields
    val hallResident: String = "",      // e.g., "Hall 3"
    val clubMember: String = "",        // e.g., "IEEE Club"
    val courseCohort: String = ""       // e.g., "EEE Year 4"
)
```

**UI Implementation** (`fragment_profile.xml`):
- Hall dropdown with all NTU halls
- Free-text club membership input
- Course cohort dropdown with major + year combinations

**Display in Trip Listings** (`BrowseTripsFragment.kt`):
```kotlin
// Load driver badges from Firestore
firestore.collection("users")
    .document(trip.driverUid)
    .get()
    .addOnSuccessListener { document ->
        val badges = mutableListOf<String>()
        
        if (!hall.isNullOrEmpty()) badges.add("🏠 $hall")
        if (!club.isNullOrEmpty()) badges.add("👥 $club")
        if (!cohort.isNullOrEmpty()) badges.add("🎓 $cohort")
        
        holder.tvDriverBadges.text = badges.joinToString(" • ")
    }
```

**Benefits**:
- Build social trust through shared affiliations
- Identify mutual connections (same hall, club, or course)
- Ice-breaker for conversations during rides

### Badge Examples

Trip listing display:
```
Driver: John Tan
🏠 Hall 3 • 👥 IEEE Club • 🎓 EEE Year 4
```

## 3. Enhanced Preferences for Safety & Comfort

### Preference Options

**Existing**:
- Gender preference (No preference / Same gender only)
- No smoking
- No pets
- Music allowed

**New**:
- 🤫 Quiet ride preference

**Data Models**:
```kotlin
// User.kt
val quietRidePreference: Boolean = false

// Trip.kt
val quietRide: Boolean = false
```

**Benefits**:
- Students can find rides matching their study/rest needs
- Clear expectations set before ride
- Reduced discomfort and conflicts

### Emergency Contact Feature

**Implementation**: `ProfileFragment.kt`

Quick dial button for NTU Security:
```kotlin
btnEmergencyCall.setOnClickListener {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:${NTULocations.NTU_SECURITY_HOTLINE}")
    }
    startActivity(intent)
}
```

**Security Hotline**: 6791 1616

**UI Display**:
- Prominent orange-colored card
- Always visible in Profile section
- Emergency info: "NTU Security Hotline: 6791 1616"

**Benefits**:
- Quick access during emergencies
- Campus-specific security support
- Peace of mind for riders

## 4. Flexible Trip Radius

### Singapore-Wide Coverage

Unlike campus-only ride sharing, this app supports:
- Trips entirely within NTU campus
- Trips from NTU to anywhere in Singapore
- Trips from anywhere in Singapore to NTU
- Trips between Singapore locations (via NTU)

**Use Cases**:
- Weekend trips home (NTU → residential areas)
- Airport runs (NTU → Changi Airport)
- Shopping trips (NTU → Orchard, Jurong East)
- Internship commutes (NTU → CBD)

### Common Routes Database

Predefined popular routes with suggested times:

| Route | Description | Suggested Times |
|-------|-------------|----------------|
| NTU → Tampines | East Side | Friday 6pm, Saturday Morning, Sunday Evening |
| NTU → Changi Airport | Airport Transfer | Early Morning, Late Evening, Weekend |
| NTU → Orchard Road | Shopping | Friday Evening, Saturday Afternoon, Sunday |
| NTU → Jurong East | MRT/JEM Mall | Weekday Evening, Weekend |
| NTU → Marina Bay | CBD/Business | Weekday Morning, Weekend |
| NTU → Woodlands | North/Causeway | Friday Evening, Saturday, Sunday Evening |
| Tampines → NTU | Return from East | Sunday Evening, Monday Morning |

## 5. User Experience Enhancements

### Signup Flow

1. Welcome screen with clear branding
2. Signup form with NTU email requirement
3. Helper text: "Must use @e.ntu.edu.sg email"
4. Email validation on submit
5. User profile creation in Firestore

### Profile Setup

1. Load existing user data
2. Gender preference selection
3. Ride preferences (smoking, pets, music, quiet)
4. NTU badges input (hall, club, cohort)
5. Emergency contact access
6. Save preferences button
7. Logout button

### Trip Posting Flow

1. View common routes for current time
2. Select origin from autocomplete
3. Select destination from autocomplete
4. Pick date and time
5. Enter available seats
6. Set trip preferences
7. Add additional notes
8. Post trip

### Trip Browsing Flow

1. Quick search bar (origin + destination)
2. Filter panel with preferences
3. Trip list with:
   - Driver info with badges
   - Route visualization (origin → destination)
   - Date/time
   - Available seats
   - Preference chips
4. Empty state for no results

## 6. Technical Implementation

### Data Structure

**Firestore Collections**:

```
users/
  {userId}/
    - uid: String
    - fullName: String
    - email: String (must end with @e.ntu.edu.sg)
    - genderPreference: String
    - noSmokingPreference: Boolean
    - noPetsPreference: Boolean
    - musicPreference: Boolean
    - quietRidePreference: Boolean
    - hallResident: String
    - clubMember: String
    - courseCohort: String
    - rating: Double
    - totalRides: Int

trips/
  {tripId}/
    - tripId: String
    - driverUid: String
    - driverName: String
    - origin: String
    - destination: String
    - dateTime: Long (timestamp)
    - seatsAvailable: Int
    - noSmoking: Boolean
    - noPets: Boolean
    - musicAllowed: Boolean
    - quietRide: Boolean
    - additionalNotes: String
    - status: String (active/completed/cancelled)
    - passengers: List<String>
```

### Key Files

- **Data Models**: `data/model/User.kt`, `data/model/Trip.kt`
- **NTU Data**: `data/NTULocations.kt`
- **Fragments**: 
  - `SignupFragment.kt` - Email validation
  - `ProfileFragment.kt` - Badges, emergency button
  - `PostTripFragment.kt` - Location autocomplete, route suggestions
  - `BrowseTripsFragment.kt` - Badge display, filtering
- **Layouts**:
  - `fragment_profile.xml` - Badge inputs, emergency card
  - `fragment_post_trip.xml` - Common routes card
  - `item_trip.xml` - Badge display line

## 7. Security & Privacy

### Email Verification
- Only @e.ntu.edu.sg emails accepted
- Firebase Authentication handles email verification
- No access without valid NTU email

### Optional Badge Display
- All badges are optional
- Users control what information to share
- Badges enhance trust but aren't mandatory

### Emergency Features
- Quick access to NTU Security
- No location tracking (Phase 1)
- User-controlled visibility

## 8. Future Enhancements

### Phase 2 Considerations

1. **Enhanced Matching**
   - Consider badge compatibility
   - Route overlap detection
   - Mutual connection notifications

2. **Social Features**
   - "You share 2 Telegram groups"
   - "Same hall block" notifications
   - Course-based ride matching

3. **Google Maps Integration**
   - Visual route preview
   - Pickup point selection
   - ETA calculation
   - Shared segment highlighting

4. **NTU Etiquette Ratings**
   - Custom rating prompts:
     - "Was the ride punctual?"
     - "Did the driver respect NTU campus rules?"
     - "Would you recommend to hall mates?"

5. **Smart Scheduling**
   - Class schedule integration
   - Hall-to-lecture matching (8am Hall 3 → South Spine)
   - Exam period special routes

## 9. Usage Statistics (Expected)

### Popular Routes
1. Hall areas → North/South Spine (Morning classes)
2. NTU → Jurong East MRT (Evening, weekends)
3. NTU → East side (Friday evening, Sunday evening)
4. Various halls ↔ Canteen areas (Meal times)
5. NTU → Changi Airport (Holiday periods)

### Peak Times
- Weekday mornings (7-9am): Campus internal
- Weekday evenings (5-7pm): Campus to external
- Friday evenings (6-9pm): Long distance external
- Sunday evenings (6-10pm): Return to campus

### Badge Adoption
- Hall badges: Expected high adoption (~70%)
- Course cohort: Expected high adoption (~80%)
- Club membership: Expected medium adoption (~40%)

## 10. Conclusion

The NTU-centric features transform a generic ride-sharing app into a tailored campus solution that:
- Builds trust through NTU-only access and profile badges
- Saves time with campus-aware location autocomplete
- Enhances safety with emergency contact features
- Provides flexibility for Singapore-wide trips
- Suggests intelligent route recommendations

These features create a more comfortable and efficient carpooling experience specifically designed for the NTU student community.
