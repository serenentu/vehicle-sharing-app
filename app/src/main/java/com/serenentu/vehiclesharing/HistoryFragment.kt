package com.serenentu.vehiclesharing

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.serenentu.vehiclesharing.data.model.Trip
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : Fragment() {
    
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private val trips = mutableListOf<Trip>()
    private lateinit var adapter: TripsAdapter
    private lateinit var rvTrips: RecyclerView
    private lateinit var tvEmptyState: TextView
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        rvTrips = view.findViewById(R.id.rvTrips)
        tvEmptyState = view.findViewById(R.id.tvEmptyState)
        
        adapter = TripsAdapter(trips) { trip ->
            showTripDetailsDialog(trip)
        }
        
        rvTrips.layoutManager = LinearLayoutManager(context)
        rvTrips.adapter = adapter
        
        return view
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(context, "Please login to view history", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Load user's trips
        loadUserTrips(currentUser.uid)
    }
    
    override fun onResume() {
        super.onResume()
        // Reload trips when fragment becomes visible
        val currentUser = auth.currentUser
        if (currentUser != null) {
            loadUserTrips(currentUser.uid)
        }
    }
    
    private fun loadUserTrips(userId: String) {
        firestore.collection("trips")
            .whereEqualTo("driverUid", userId)
            .get()
            .addOnSuccessListener { documents ->
                trips.clear()
                val currentTime = System.currentTimeMillis()
                
                for (document in documents) {
                    try {
                        val trip = document.toObject(Trip::class.java)
                        
                        // Auto-update status to inactive if trip date has passed
                        if (trip.dateTime < currentTime && trip.status == "active") {
                            firestore.collection("trips")
                                .document(trip.tripId)
                                .update("status", "inactive")
                            // Update local copy
                            trips.add(trip.copy(status = "inactive"))
                        } else {
                            trips.add(trip)
                        }
                    } catch (e: Exception) {
                        // Skip invalid trip data
                        continue
                    }
                }
                
                // Sort trips by dateTime in descending order (newest first)
                trips.sortByDescending { it.dateTime }
                
                adapter.notifyDataSetChanged()
                
                if (trips.isEmpty()) {
                    rvTrips.visibility = View.GONE
                    tvEmptyState.visibility = View.VISIBLE
                } else {
                    rvTrips.visibility = View.VISIBLE
                    tvEmptyState.visibility = View.GONE
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to load trips: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    
    private fun showTripDetailsDialog(trip: Trip) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_trip_details, null)
        
        val tvDialogOrigin = dialogView.findViewById<TextView>(R.id.tvDialogOrigin)
        val tvDialogDestination = dialogView.findViewById<TextView>(R.id.tvDialogDestination)
        val tvDialogDateTime = dialogView.findViewById<TextView>(R.id.tvDialogDateTime)
        val tvDialogSeats = dialogView.findViewById<TextView>(R.id.tvDialogSeats)
        val tvDialogPrice = dialogView.findViewById<TextView>(R.id.tvDialogPrice)
        val tvDialogStatus = dialogView.findViewById<TextView>(R.id.tvDialogStatus)
        val tvDialogNotes = dialogView.findViewById<TextView>(R.id.tvDialogNotes)
        val cardBookings = dialogView.findViewById<MaterialCardView>(R.id.cardBookings)
        val tvBookingInfo = dialogView.findViewById<TextView>(R.id.tvBookingInfo)
        val tvPassengersList = dialogView.findViewById<TextView>(R.id.tvPassengersList)
        val btnEdit = dialogView.findViewById<Button>(R.id.btnEdit)
        val btnDelete = dialogView.findViewById<Button>(R.id.btnDelete)
        val btnClose = dialogView.findViewById<Button>(R.id.btnClose)
        
        val dateFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
        val dateStr = if (trip.dateTime > 0) dateFormat.format(Date(trip.dateTime)) else "Date not set"
        
        tvDialogOrigin.text = trip.origin
        tvDialogDestination.text = trip.destination
        tvDialogDateTime.text = dateStr
        tvDialogSeats.text = "${trip.seatsAvailable} seats available"
        tvDialogPrice.text = String.format("$%.2f per seat", trip.pricePerSeat)
        tvDialogStatus.text = trip.status.capitalize()
        tvDialogNotes.text = if (trip.additionalNotes.isEmpty()) "No notes" else trip.additionalNotes
        
        // Load and display booking information
        loadBookingInfo(trip.tripId, cardBookings, tvBookingInfo, tvPassengersList)
        
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Trip Details")
            .setView(dialogView)
            .create()
        
        btnEdit.setOnClickListener {
            dialog.dismiss()
            // Navigate to EditTripFragment
            val bundle = bundleOf("tripId" to trip.tripId)
            findNavController().navigate(R.id.action_historyFragment_to_editTripFragment, bundle)
        }
        
        btnDelete.setOnClickListener {
            showDeleteConfirmation(trip, dialog)
        }
        
        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        
        dialog.show()
    }
    
    private fun loadBookingInfo(tripId: String, cardBookings: MaterialCardView, tvBookingInfo: TextView, tvPassengersList: TextView) {
        firestore.collection("bookings")
            .whereEqualTo("tripId", tripId)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    cardBookings.visibility = View.GONE
                } else {
                    cardBookings.visibility = View.VISIBLE
                    val bookingCount = documents.size()
                    val passengers = documents.mapNotNull { it.getString("passengerName") }
                    
                    tvBookingInfo.text = "$bookingCount booking${if (bookingCount != 1) "s" else ""}"
                    
                    if (passengers.isNotEmpty()) {
                        tvPassengersList.text = "Passengers:\n• ${passengers.joinToString("\n• ")}"
                        tvPassengersList.visibility = View.VISIBLE
                    } else {
                        tvPassengersList.visibility = View.GONE
                    }
                }
            }
            .addOnFailureListener {
                // Silently fail - bookings are optional information
                cardBookings.visibility = View.GONE
            }
    }
    
    private fun showDeleteConfirmation(trip: Trip, parentDialog: AlertDialog) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Trip")
            .setMessage("Are you sure you want to delete this trip?")
            .setPositiveButton("Delete") { _, _ ->
                deleteTrip(trip, parentDialog)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun deleteTrip(trip: Trip, dialog: AlertDialog) {
        firestore.collection("trips")
            .document(trip.tripId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(context, "Trip deleted successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                // Reload trips
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    loadUserTrips(currentUser.uid)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to delete trip: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    
    inner class TripsAdapter(
        private val trips: List<Trip>,
        private val onItemClick: (Trip) -> Unit
    ) : RecyclerView.Adapter<TripsAdapter.TripViewHolder>() {
        
        inner class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvDateTime: TextView = itemView.findViewById(R.id.tvDateTime)
            val tvOrigin: TextView = itemView.findViewById(R.id.tvOrigin)
            val tvDestination: TextView = itemView.findViewById(R.id.tvDestination)
            val tvTripDetails: TextView = itemView.findViewById(R.id.tvTripDetails)
            val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_history_trip, parent, false)
            return TripViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
            val trip = trips[position]
            val dateFormat = SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault())
            val dateStr = if (trip.dateTime > 0) dateFormat.format(Date(trip.dateTime)) else "Date not set"
            
            holder.tvDateTime.text = dateStr
            holder.tvOrigin.text = trip.origin
            holder.tvDestination.text = trip.destination
            holder.tvTripDetails.text = "${trip.seatsAvailable} seats • $${String.format("%.2f", trip.pricePerSeat)}/seat"
            holder.tvStatus.text = trip.status.capitalize()
            
            // Set status color
            when (trip.status.lowercase()) {
                "active" -> holder.tvStatus.backgroundTintList = 
                    android.content.res.ColorStateList.valueOf(resources.getColor(R.color.success, null))
                "inactive" -> holder.tvStatus.backgroundTintList = 
                    android.content.res.ColorStateList.valueOf(resources.getColor(R.color.text_secondary, null))
                "completed" -> holder.tvStatus.backgroundTintList = 
                    android.content.res.ColorStateList.valueOf(resources.getColor(R.color.info, null))
                else -> holder.tvStatus.backgroundTintList = 
                    android.content.res.ColorStateList.valueOf(resources.getColor(R.color.text_hint, null))
            }
            
            holder.itemView.setOnClickListener {
                onItemClick(trip)
            }
        }
        
        override fun getItemCount() = trips.size
    }
}
