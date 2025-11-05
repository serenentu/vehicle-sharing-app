# Quick Reference Guide

This guide provides quick code snippets and patterns for common operations in the Vehicle Sharing App.

## Firebase Authentication

### Check if User is Logged In
```kotlin
val auth = FirebaseAuth.getInstance()
val currentUser = auth.currentUser

if (currentUser != null) {
    // User is logged in
    val userId = currentUser.uid
    val email = currentUser.email
} else {
    // User is not logged in
}
```

### Sign Up New User
```kotlin
auth.createUserWithEmailAndPassword(email, password)
    .addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val user = auth.currentUser
            // User created successfully
        } else {
            // Sign up failed
            val error = task.exception?.message
        }
    }
```

### Login User
```kotlin
auth.signInWithEmailAndPassword(email, password)
    .addOnCompleteListener { task ->
        if (task.isSuccessful) {
            // Login successful
        } else {
            // Login failed
        }
    }
```

### Logout User
```kotlin
auth.signOut()
// Navigate to welcome/login screen
```

## Firestore Operations

### Save Document
```kotlin
val firestore = FirebaseFirestore.getInstance()
val data = hashMapOf(
    "field1" to "value1",
    "field2" to "value2"
)

firestore.collection("collectionName")
    .document("documentId")
    .set(data)
    .addOnSuccessListener {
        // Document saved successfully
    }
    .addOnFailureListener { e ->
        // Error saving document
    }
```

### Get Document
```kotlin
firestore.collection("users")
    .document(userId)
    .get()
    .addOnSuccessListener { document ->
        if (document.exists()) {
            val user = document.toObject(User::class.java)
            // Use user object
        }
    }
    .addOnFailureListener { e ->
        // Error getting document
    }
```

### Update Document
```kotlin
val updates = hashMapOf<String, Any>(
    "field1" to "newValue",
    "field2" to true
)

firestore.collection("users")
    .document(userId)
    .update(updates)
    .addOnSuccessListener {
        // Update successful
    }
    .addOnFailureListener { e ->
        // Update failed
    }
```

### Query Collection
```kotlin
firestore.collection("trips")
    .whereEqualTo("status", "active")
    .orderBy("dateTime", Query.Direction.ASCENDING)
    .get()
    .addOnSuccessListener { documents ->
        for (document in documents) {
            val trip = document.toObject(Trip::class.java)
            // Process each trip
        }
    }
    .addOnFailureListener { e ->
        // Query failed
    }
```

## Navigation

### Navigate to Another Fragment
```kotlin
import androidx.navigation.fragment.findNavController

// Navigate using action
findNavController().navigate(R.id.action_sourceFragment_to_destinationFragment)

// Navigate using destination ID
findNavController().navigate(R.id.destinationFragment)
```

### Navigate with Arguments
```kotlin
val bundle = Bundle().apply {
    putString("key", "value")
}
findNavController().navigate(R.id.destinationFragment, bundle)
```

### Pop Back Stack
```kotlin
// Go back to previous screen
findNavController().popBackStack()

// Pop to a specific destination
findNavController().popBackStack(R.id.destinationFragment, false)
```

## Date and Time

### Show Date Picker
```kotlin
val calendar = Calendar.getInstance()

DatePickerDialog(
    requireContext(),
    { _, year, month, day ->
        // Date selected
        calendar.set(year, month, day)
    },
    calendar.get(Calendar.YEAR),
    calendar.get(Calendar.MONTH),
    calendar.get(Calendar.DAY_OF_MONTH)
).show()
```

### Show Time Picker
```kotlin
TimePickerDialog(
    requireContext(),
    { _, hour, minute ->
        // Time selected
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
    },
    calendar.get(Calendar.HOUR_OF_DAY),
    calendar.get(Calendar.MINUTE),
    false // 24-hour format
).show()
```

### Format Date
```kotlin
val dateFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
val dateString = dateFormat.format(calendar.time)
// Output: "Jan 01, 2024 02:30 PM"
```

### Get Timestamp
```kotlin
val timestamp = System.currentTimeMillis()
// or
val timestamp = Calendar.getInstance().timeInMillis
```

## UI Components

### Show Toast Message
```kotlin
Toast.makeText(context, "Message", Toast.LENGTH_SHORT).show()
Toast.makeText(context, "Long message", Toast.LENGTH_LONG).show()
```

### Enable/Disable Button
```kotlin
button.isEnabled = false  // Disable
button.isEnabled = true   // Enable
```

### Show/Hide View
```kotlin
view.visibility = View.VISIBLE   // Show
view.visibility = View.GONE      // Hide (doesn't take space)
view.visibility = View.INVISIBLE // Hide (takes space)
```

### Get Text from EditText
```kotlin
val text = editText.text.toString()
val trimmedText = editText.text.toString().trim()
```

### Set Text to TextView
```kotlin
textView.text = "Some text"
// or
editText.setText("Some text")
```

## Validation

### Check Empty Field
```kotlin
if (text.isEmpty()) {
    Toast.makeText(context, "Field cannot be empty", Toast.LENGTH_SHORT).show()
    return
}
```

### Validate Email
```kotlin
if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
    Toast.makeText(context, "Invalid email format", Toast.LENGTH_SHORT).show()
    return
}
```

### Validate Password Length
```kotlin
if (password.length < 6) {
    Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
    return
}
```

### Compare Passwords
```kotlin
if (password != confirmPassword) {
    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
    return
}
```

## RecyclerView

### Setup RecyclerView
```kotlin
// In Fragment/Activity
val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
recyclerView.layoutManager = LinearLayoutManager(context)
recyclerView.adapter = myAdapter
```

### Notify Data Changed
```kotlin
adapter.notifyDataSetChanged()  // Refresh all items
adapter.notifyItemInserted(position)  // Notify single item added
adapter.notifyItemRemoved(position)   // Notify single item removed
```

## Common Patterns

### Loading State Pattern
```kotlin
// Show loading
progressBar.visibility = View.VISIBLE
button.isEnabled = false

// API call
performOperation()
    .addOnSuccessListener {
        // Hide loading
        progressBar.visibility = View.GONE
        button.isEnabled = true
        // Handle success
    }
    .addOnFailureListener { e ->
        // Hide loading
        progressBar.visibility = View.GONE
        button.isEnabled = true
        // Handle error
        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
    }
```

### Empty State Pattern
```kotlin
if (items.isEmpty()) {
    recyclerView.visibility = View.GONE
    emptyStateTextView.visibility = View.VISIBLE
} else {
    recyclerView.visibility = View.VISIBLE
    emptyStateTextView.visibility = View.GONE
}
```

## Debugging

### Log Messages
```kotlin
import android.util.Log

Log.d("TAG", "Debug message")
Log.i("TAG", "Info message")
Log.w("TAG", "Warning message")
Log.e("TAG", "Error message")
Log.v("TAG", "Verbose message")
```

### Print Stack Trace
```kotlin
try {
    // Code that might throw exception
} catch (e: Exception) {
    e.printStackTrace()
    Log.e("TAG", "Error: ${e.message}", e)
}
```

## Best Practices

### 1. Always Check for Null
```kotlin
val user = auth.currentUser
if (user != null) {
    // Safe to use user
}
```

### 2. Use Safe Calls
```kotlin
val email = user?.email ?: "No email"
```

### 3. Handle Errors Gracefully
```kotlin
.addOnFailureListener { e ->
    Toast.makeText(context, "Operation failed: ${e.message}", Toast.LENGTH_SHORT).show()
}
```

### 4. Disable Buttons During Operations
```kotlin
button.isEnabled = false
performOperation()
    .addOnCompleteListener {
        button.isEnabled = true
    }
```

### 5. Use String Resources
```xml
<!-- strings.xml -->
<string name="error_empty_field">Field cannot be empty</string>
```
```kotlin
// In code
Toast.makeText(context, getString(R.string.error_empty_field), Toast.LENGTH_SHORT).show()
```

### 6. Clean Up Resources
```kotlin
override fun onDestroyView() {
    super.onDestroyView()
    // Clean up resources
}
```

## Testing Tips

### Test Authentication
1. Create test account with valid email
2. Try login with wrong password
3. Try signup with existing email
4. Test logout and re-login

### Test Firestore Operations
1. Check Firebase Console to verify data
2. Test with slow network (airplane mode on/off)
3. Test with multiple users
4. Verify security rules work

### Common Issues

**Issue**: Firebase not initialized
**Solution**: Ensure google-services.json is in app/ directory

**Issue**: Navigation action not found
**Solution**: Rebuild project (Build → Clean Project → Rebuild Project)

**Issue**: Data not saving to Firestore
**Solution**: Check Firebase Console rules, verify internet connection

**Issue**: App crashes on button click
**Solution**: Check Logcat for NullPointerException, ensure views are properly initialized
