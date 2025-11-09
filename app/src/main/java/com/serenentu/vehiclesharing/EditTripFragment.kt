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
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.serenentu.vehiclesharing.data.NTULocations
import com.serenentu.vehiclesharing.data.model.Trip
import java.text.SimpleDateFormat
import java.util.*

class EditTripFragment : Fragment() {
    
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var selectedDateTime: Long? = null
    private var tripId: String? = null
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_trip, container, false)
        
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        // Get trip ID from arguments
        tripId = arguments?.getString("tripId")
        
        if (tripId == null) {
            Toast.makeText(context, "Error: Trip not found", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
            return view
        }
        
        val etOrigin = view.findViewById<AutoCompleteTextView>(R.id.etOrigin)
        val etDestination = view.findViewById<AutoCompleteTextView>(R.id.etDestination)
        val etDateTime = view.findViewById<EditText>(R.id.etDateTime)
        val etSeatsAvailable = view.findViewById<EditText>(R.id.etSeatsAvailable)
        val etPricePerSeat = view.findViewById<EditText>(R.id.etPricePerSeat)
        val cbNoSmoking = view.findViewById<CheckBox>(R.id.cbNoSmoking)
        val cbNoPets = view.findViewById<CheckBox>(R.id.cbNoPets)
        val cbMusicAllowed = view.findViewById<CheckBox>(R.id.cbMusicAllowed)
        val cbQuietRide = view.findViewById<CheckBox>(R.id.cbQuietRide)
        val etAdditionalNotes = view.findViewById<EditText>(R.id.etAdditionalNotes)
        val btnSaveTrip = view.findViewById<Button>(R.id.btnSaveTrip)
        
        // Setup location autocomplete
        val locationAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            NTULocations.ALL_LOCATIONS
        )
        etOrigin.setAdapter(locationAdapter)
        etDestination.setAdapter(locationAdapter)
        
        // Load trip data
        loadTripData(
            etOrigin, etDestination, etDateTime, etSeatsAvailable, etPricePerSeat,
            cbNoSmoking, cbNoPets, cbMusicAllowed, cbQuietRide, etAdditionalNotes
        )
        
        // Date and time picker
        etDateTime.setOnClickListener {
            showDateTimePicker(etDateTime)
        }
        
        btnSaveTrip.setOnClickListener {
            val currentUser = auth.currentUser
            if (currentUser == null) {
                Toast.makeText(context, "Please login to edit trip", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val origin = etOrigin.text.toString().trim()
            val destination = etDestination.text.toString().trim()
            val seatsText = etSeatsAvailable.text.toString().trim()
            val priceText = etPricePerSeat.text.toString().trim()
            val additionalNotes = etAdditionalNotes.text.toString().trim()
            
            // Validation
            if (origin.isEmpty() || destination.isEmpty() || seatsText.isEmpty() || priceText.isEmpty()) {
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
            
            val price = priceText.toDoubleOrNull()
            if (price == null || price < 0) {
                Toast.makeText(context, "Please enter valid price", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            btnSaveTrip.isEnabled = false
            
            // Update trip in Firestore
            val updates = hashMapOf<String, Any>(
                "origin" to origin,
                "destination" to destination,
                "dateTime" to selectedDateTime!!,
                "seatsAvailable" to seats,
                "pricePerSeat" to price,
                "noSmoking" to cbNoSmoking.isChecked,
                "noPets" to cbNoPets.isChecked,
                "musicAllowed" to cbMusicAllowed.isChecked,
                "quietRide" to cbQuietRide.isChecked,
                "additionalNotes" to additionalNotes
            )
            
            firestore.collection("trips")
                .document(tripId!!)
                .update(updates)
                .addOnSuccessListener {
                    Toast.makeText(context, "Trip updated successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
                .addOnFailureListener { e ->
                    btnSaveTrip.isEnabled = true
                    Toast.makeText(
                        context,
                        "Failed to update trip: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
        
        return view
    }
    
    private fun loadTripData(
        etOrigin: AutoCompleteTextView,
        etDestination: AutoCompleteTextView,
        etDateTime: EditText,
        etSeatsAvailable: EditText,
        etPricePerSeat: EditText,
        cbNoSmoking: CheckBox,
        cbNoPets: CheckBox,
        cbMusicAllowed: CheckBox,
        cbQuietRide: CheckBox,
        etAdditionalNotes: EditText
    ) {
        firestore.collection("trips")
            .document(tripId!!)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val trip = document.toObject(Trip::class.java)
                    if (trip != null) {
                        // Verify ownership - only trip owner can edit
                        if (trip.driverUid != auth.currentUser?.uid) {
                            Toast.makeText(context, "You don't have permission to edit this trip", Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                            return@addOnSuccessListener
                        }
                        
                        etOrigin.setText(trip.origin)
                        etDestination.setText(trip.destination)
                        etSeatsAvailable.setText(trip.seatsAvailable.toString())
                        etPricePerSeat.setText(trip.pricePerSeat.toString())
                        cbNoSmoking.isChecked = trip.noSmoking
                        cbNoPets.isChecked = trip.noPets
                        cbMusicAllowed.isChecked = trip.musicAllowed
                        cbQuietRide.isChecked = trip.quietRide
                        etAdditionalNotes.setText(trip.additionalNotes)
                        
                        // Set date time
                        selectedDateTime = trip.dateTime
                        val dateFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
                        etDateTime.setText(dateFormat.format(Date(trip.dateTime)))
                    }
                } else {
                    Toast.makeText(context, "Trip not found", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to load trip: ${e.message}", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
    }
    
    private fun showDateTimePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        if (selectedDateTime != null) {
            calendar.timeInMillis = selectedDateTime!!
        }
        
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
