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

### 3. Bookings Collection (`bookings`)

Stores booking/reservation information when passengers book rides.

**Document ID**: Auto-generated unique booking ID

**Fields**:
- `bookingId` (String): Unique identifier for the booking
- `tripId` (String): ID of the trip being booked
- `passengerUid` (String): UID of the passenger making the booking
- `passengerName` (String): Name of the passenger
- `driverUid` (String): UID of the driver (from the trip)
- `driverName` (String): Name of the driver (from the trip)
- `origin` (String): Starting location (copied from trip)
- `destination` (String): Destination location (copied from trip)
- `dateTime` (Long): Timestamp of the trip (milliseconds since epoch)
- `seatsBooked` (Integer): Number of seats booked (default: 1)
- `totalPrice` (Double): Total price for the booking
- `paymentMethod` (String): Payment method selected
  - Values: `"cash"`, `"paynow"`, `"card"`
- `bookingStatus` (String): Current status of the booking
  - Values: `"pending"`, `"confirmed"`, `"completed"`, `"cancelled"`
- `bookingTime` (Long): Timestamp when booking was created (milliseconds since epoch)

**Example Document**:
```json
{
  "bookingId": "booking789",
  "tripId": "trip456",
  "passengerUid": "user789",
  "passengerName": "Jane Smith",
  "driverUid": "user123",
  "driverName": "John Doe",
  "origin": "NTU Hall 1",
  "destination": "Tampines Mall",
  "dateTime": 1672531200000,
  "seatsBooked": 1,
  "totalPrice": 5.00,
  "paymentMethod": "cash",
  "bookingStatus": "confirmed",
  "bookingTime": 1672520000000
}
```

## Indexes

The app performs client-side sorting to avoid the need for composite indexes in Firestore. This makes the app work out-of-the-box without requiring manual index configuration in the Firebase Console.

**Note**: No composite indexes are required for the current implementation. All queries use simple equality checks (`.whereEqualTo()`) and sorting is performed in the app after data retrieval.

## Security Rules

**IMPORTANT**: These security rules must be deployed to Firebase Firestore for the app to function properly. Without these rules, users will encounter "permission denied" errors when trying to create bookings or perform other operations.

**To deploy these rules:**
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Select your project
3. Navigate to Firestore Database → Rules
4. Copy and paste the rules below
5. Click "Publish"

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
      // Allow driver to fully update/delete their trip
      // Allow any authenticated user to update trip for booking purposes
      // (passengers list, bookedSeats, seatsAvailable)
      // Note: In production, consider using Cloud Functions for booking updates
      allow update: if request.auth != null;
      allow delete: if request.auth != null && request.auth.uid == resource.data.driverUid;
    }
    
    // Bookings can be created by authenticated users
    // Only the passenger or driver can read their own bookings
    match /bookings/{bookingId} {
      allow create: if request.auth != null;
      allow read: if request.auth != null && 
                  (resource.data.passengerUid == request.auth.uid ||
                   resource.data.driverUid == request.auth.uid);
      allow update, delete: if request.auth != null && 
                            (resource.data.passengerUid == request.auth.uid ||
                             resource.data.driverUid == request.auth.uid);
    }
  }
}
```

## Future Enhancements

### Phase 2 Considerations:
- Add `ratings` collection for trip reviews
- Add `messages` collection for in-app chat
- Implement geolocation fields for map integration
- Add `notifications` collection for push notifications
- Enhance booking cancellation and refund workflow
