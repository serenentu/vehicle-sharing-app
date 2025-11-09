# Firestore Security Rules Deployment Guide

## Problem: "Failed to create booking: permission denied"

If you're seeing this error when trying to book a ride, it means your Firestore security rules need to be updated to include rules for the `bookings` collection.

## Quick Fix

Follow these steps to deploy the updated security rules:

### Step 1: Access Firebase Console
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Select your project (the one linked to this app)

### Step 2: Navigate to Firestore Rules
1. In the left sidebar, click on **Firestore Database**
2. Click on the **Rules** tab at the top

### Step 3: Update the Rules
Copy and paste the following security rules (this will replace your existing rules):

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

### Step 4: Publish the Rules
1. Click the **Publish** button in the top right
2. Wait for the confirmation message

### Step 5: Test the Fix
1. Close and restart your app
2. Try to book a ride again
3. The booking should now work without permission errors

## What These Rules Do

### Users Collection
- Any authenticated user can read user profiles
- Users can only modify their own profile

### Trips Collection
- Any authenticated user can read active trips
- Only the driver who created a trip can update or delete it

### Bookings Collection (NEW)
- Any authenticated user can create a booking
- Only the passenger or driver involved in a booking can read it
- Only the passenger or driver involved can update or cancel their booking

## Troubleshooting

### Still Getting Permission Denied?
1. Make sure you're logged in to the app
2. Check that you published the rules (not just saved as draft)
3. Try logging out and logging back in
4. Clear app data and restart

### Rules Won't Publish?
- Check for syntax errors (the console will highlight them)
- Make sure you copied the entire rules block including the outer `service cloud.firestore { }` wrapper

### Need Help?
- Double-check you're in the correct Firebase project
- Verify your `google-services.json` file matches your Firebase project
- Check the Firebase Console for any error messages

## Security Notes

These rules ensure:
- ✅ Users must be authenticated to perform any operations
- ✅ Bookings are private - only the passenger and driver can see their bookings
- ✅ Users cannot book trips for other users (enforced at app level)
- ✅ Only trip owners can modify their trips
- ✅ All sensitive data is protected

For production deployment, consider:
- Adding rate limiting to prevent spam bookings
- Implementing booking validation (e.g., checking seat availability server-side)
- Adding audit logs for compliance
