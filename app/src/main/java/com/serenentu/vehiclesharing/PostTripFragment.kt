package com.serenentu.vehiclesharing

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.serenentu.vehiclesharing.data.NTULocations
import com.serenentu.vehiclesharing.data.model.Trip
import java.text.SimpleDateFormat
import java.util.*

class PostTripFragment : Fragment() {
    
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var selectedDateTime: Long? = null
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_trip, container, false)
        
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        val etOrigin = view.findViewById<AutoCompleteTextView>(R.id.etOrigin)
        val etDestination = view.findViewById<AutoCompleteTextView>(R.id.etDestination)
        val etDateTime = view.findViewById<EditText>(R.id.etDateTime)
        val etSeatsAvailable = view.findViewById<EditText>(R.id.etSeatsAvailable)
        val cbNoSmoking = view.findViewById<CheckBox>(R.id.cbNoSmoking)
        val cbNoPets = view.findViewById<CheckBox>(R.id.cbNoPets)
        val cbMusicAllowed = view.findViewById<CheckBox>(R.id.cbMusicAllowed)
        val cbQuietRide = view.findViewById<CheckBox>(R.id.cbQuietRide)
        val etAdditionalNotes = view.findViewById<EditText>(R.id.etAdditionalNotes)
        val btnPostTrip = view.findViewById<Button>(R.id.btnPostTrip)
        
        // Setup location autocomplete
        val locationAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            NTULocations.ALL_LOCATIONS
        )
        etOrigin.setAdapter(locationAdapter)
        etDestination.setAdapter(locationAdapter)
        
        // Date and time picker
        etDateTime.setOnClickListener {
            showDateTimePicker(etDateTime)
        }
        
        btnPostTrip.setOnClickListener {
            val currentUser = auth.currentUser
            if (currentUser == null) {
                Toast.makeText(context, "Please login to post a trip", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val origin = etOrigin.text.toString().trim()
            val destination = etDestination.text.toString().trim()
            val seatsText = etSeatsAvailable.text.toString().trim()
            val additionalNotes = etAdditionalNotes.text.toString().trim()
            
            // Validation
            if (origin.isEmpty() || destination.isEmpty() || seatsText.isEmpty()) {
                Toast.makeText(context, "Please fill in required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            if (selectedDateTime == null) {
                Toast.makeText(context, "Please select date and time", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val seats = seatsText.toIntOrNull()
            if (seats == null || seats <= 0) {
                Toast.makeText(context, "Please enter valid number of seats", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            btnPostTrip.isEnabled = false
            
            // Get user name from Firestore
            firestore.collection("users")
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    val userName = document.getString("fullName") ?: "Unknown"
                    
                    // Create trip object
                    val tripId = firestore.collection("trips").document().id
                    val trip = Trip(
                        tripId = tripId,
                        driverUid = currentUser.uid,
                        driverName = userName,
                        origin = origin,
                        destination = destination,
                        dateTime = selectedDateTime!!,
                        seatsAvailable = seats,
                        noSmoking = cbNoSmoking.isChecked,
                        noPets = cbNoPets.isChecked,
                        musicAllowed = cbMusicAllowed.isChecked,
                        quietRide = cbQuietRide.isChecked,
                        additionalNotes = additionalNotes,
                        status = "active"
                    )
                    
                    // Save to Firestore
                    firestore.collection("trips")
                        .document(tripId)
                        .set(trip)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Trip posted successfully", Toast.LENGTH_SHORT).show()
                            // Clear form
                            etOrigin.text.clear()
                            etDestination.text.clear()
                            etDateTime.text.clear()
                            etSeatsAvailable.text.clear()
                            etAdditionalNotes.text.clear()
                            cbNoSmoking.isChecked = false
                            cbNoPets.isChecked = false
                            cbMusicAllowed.isChecked = false
                            cbQuietRide.isChecked = false
                            selectedDateTime = null
                            btnPostTrip.isEnabled = true
                        }
                        .addOnFailureListener { e ->
                            btnPostTrip.isEnabled = true
                            Toast.makeText(
                                context,
                                "Failed to post trip: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                }
                .addOnFailureListener { e ->
                    btnPostTrip.isEnabled = true
                    Toast.makeText(context, "Failed to get user info: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
        
        return view
    }
    
    private fun showDateTimePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                TimePickerDialog(
                    requireContext(),
                    { _, hour, minute ->
                        calendar.set(year, month, day, hour, minute)
                        selectedDateTime = calendar.timeInMillis
                        
                        val dateFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
                        editText.setText(dateFormat.format(calendar.time))
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false
                ).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}
