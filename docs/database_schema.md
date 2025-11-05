# Database Schema Documentation

## Overview

The Vehicle Sharing App uses Firebase Firestore as the backend database for real-time data synchronization and Firebase Authentication for user management.

## Collections

### 1. Users Collection (`users`)

Stores user profile information and preferences.

**Document ID**: User's Firebase Authentication UID

**Fields**:
- `uid` (String): User's unique identifier from Firebase Auth
- `fullName` (String): User's full name
- `email` (String): User's email address
- `genderPreference` (String): Gender preference for ride sharing
  - Values: `"no_preference"`, `"same_gender"`
- `noSmokingPreference` (Boolean): Prefers non-smoking rides
- `noPetsPreference` (Boolean): Prefers rides without pets
- `musicPreference` (Boolean): Enjoys music during rides
- `rating` (Double): Average rating from 0.0 to 5.0
- `totalRides` (Integer): Total number of completed rides

**Example Document**:
```json
{
  "uid": "user123",
  "fullName": "John Doe",
  "email": "john@example.com",
  "genderPreference": "no_preference",
  "noSmokingPreference": true,
  "noPetsPreference": false,
  "musicPreference": true,
  "rating": 4.5,
  "totalRides": 10
}
```

### 2. Trips Collection (`trips`)

Stores information about posted trips.

**Document ID**: Auto-generated unique trip ID

**Fields**:
- `tripId` (String): Unique identifier for the trip
- `driverUid` (String): UID of the driver who posted the trip
- `driverName` (String): Name of the driver
- `origin` (String): Starting location
- `destination` (String): Destination location
- `dateTime` (Long): Timestamp of the trip (milliseconds since epoch)
- `seatsAvailable` (Integer): Number of available seats
- `noSmoking` (Boolean): No smoking allowed
- `noPets` (Boolean): No pets allowed
- `musicAllowed` (Boolean): Music is allowed during the trip
- `additionalNotes` (String): Additional information about the trip
- `status` (String): Current status of the trip
  - Values: `"active"`, `"completed"`, `"cancelled"`
- `passengers` (Array of Strings): List of passenger UIDs who have joined

**Example Document**:
```json
{
  "tripId": "trip456",
  "driverUid": "user123",
  "driverName": "John Doe",
  "origin": "University Campus",
  "destination": "City Center",
  "dateTime": 1672531200000,
  "seatsAvailable": 3,
  "noSmoking": true,
  "noPets": false,
  "musicAllowed": true,
  "additionalNotes": "Leaving after class at 5 PM",
  "status": "active",
  "passengers": ["user789"]
}
```

## Indexes

For optimal query performance, the following Firestore indexes should be created:

1. **Trips by Date and Status**:
   - Collection: `trips`
   - Fields: `status` (Ascending), `dateTime` (Ascending)

2. **Trips by Driver**:
   - Collection: `trips`
   - Fields: `driverUid` (Ascending), `dateTime` (Descending)

3. **Active Trips Search**:
   - Collection: `trips`
   - Fields: `status` (Ascending), `origin` (Ascending), `destination` (Ascending)

## Security Rules

Basic Firestore security rules:

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

## Future Enhancements

### Phase 2 Considerations:
- Add `ratings` collection for trip reviews
- Add `bookings` collection for ride requests
- Add `messages` collection for in-app chat
- Implement geolocation fields for map integration
- Add `notifications` collection for push notifications
