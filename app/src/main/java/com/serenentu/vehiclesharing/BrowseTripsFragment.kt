package com.serenentu.vehiclesharing

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.serenentu.vehiclesharing.data.model.Booking
import com.serenentu.vehiclesharing.data.model.Trip
import java.text.SimpleDateFormat
import java.util.*

class BrowseTripsFragment : Fragment() {
    
    private lateinit var firestore: FirebaseFirestore
    private lateinit var rvTrips: RecyclerView
    private lateinit var tvEmptyState: TextView
    private lateinit var tripsAdapter: TripsAdapter
    private val trips = mutableListOf<Trip>()
    private val userBadgesCache = mutableMapOf<String, String>() // Cache for user badges
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_browse_trips, container, false)
        
        firestore = FirebaseFirestore.getInstance()
        
        rvTrips = view.findViewById(R.id.rvTrips)
        tvEmptyState = view.findViewById(R.id.tvEmptyState)
        val btnFilter = view.findViewById<Button>(R.id.btnFilter)
        val filterPanel = view.findViewById<LinearLayout>(R.id.filterPanel)
        val btnApplyFilters = view.findViewById<Button>(R.id.btnApplyFilters)
        val etSearchOrigin = view.findViewById<EditText>(R.id.etSearchOrigin)
        val etSearchDestination = view.findViewById<EditText>(R.id.etSearchDestination)
        val cbFilterNoSmoking = view.findViewById<CheckBox>(R.id.cbFilterNoSmoking)
        val cbFilterNoPets = view.findViewById<CheckBox>(R.id.cbFilterNoPets)
        val cbFilterMusic = view.findViewById<CheckBox>(R.id.cbFilterMusic)
        val cbFilterQuietRide = view.findViewById<CheckBox>(R.id.cbFilterQuietRide)
        
        // Setup RecyclerView
        tripsAdapter = TripsAdapter(trips, userBadgesCache)
        rvTrips.layoutManager = LinearLayoutManager(context)
        rvTrips.adapter = tripsAdapter
        
        // Toggle filter panel
        btnFilter.setOnClickListener {
            filterPanel.visibility = if (filterPanel.visibility == View.VISIBLE) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
        
        // Apply filters
        btnApplyFilters.setOnClickListener {
            val origin = etSearchOrigin.text.toString().trim()
            val destination = etSearchDestination.text.toString().trim()
            loadTrips(
                origin,
                destination,
                cbFilterNoSmoking.isChecked,
                cbFilterNoPets.isChecked,
                cbFilterMusic.isChecked,
                cbFilterQuietRide.isChecked
            )
            filterPanel.visibility = View.GONE
        }
        
        // Load all trips initially
        loadTrips()
        
        return view
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        // Clear cache to prevent memory leaks
        userBadgesCache.clear()
        trips.clear()
    }
    
    private fun loadTrips(
        origin: String = "",
        destination: String = "",
        noSmoking: Boolean = false,
        noPets: Boolean = false,
        musicAllowed: Boolean = false,
        quietRide: Boolean = false
    ) {
        // Check if fragment is added to avoid crashes
        if (!isAdded) return
        
        firestore.collection("trips")
            .whereEqualTo("status", "active")
            .get()
            .addOnSuccessListener { documents ->
                // Check if fragment is still added before processing
                if (!isAdded) return@addOnSuccessListener
                
                trips.clear()
                val driverUids = mutableSetOf<String>()
                
                for (document in documents) {
                    try {
                        val trip = document.toObject(Trip::class.java)
                        
                        // Apply filters
                        var matches = true
                        
                        if (origin.isNotEmpty() && !trip.origin.contains(origin, ignoreCase = true)) {
                            matches = false
                        }
                        
                        if (destination.isNotEmpty() && !trip.destination.contains(destination, ignoreCase = true)) {
                            matches = false
                        }
                        
                        if (noSmoking && !trip.noSmoking) {
                            matches = false
                        }
                        
                        if (noPets && !trip.noPets) {
                            matches = false
                        }
                        
                        if (musicAllowed && !trip.musicAllowed) {
                            matches = false
                        }
                        
                        if (quietRide && !trip.quietRide) {
                            matches = false
                        }
                        
                        if (matches) {
                            trips.add(trip)
                            driverUids.add(trip.driverUid)
                        }
                    } catch (e: Exception) {
                        // Skip invalid trip data
                        continue
                    }
                }
                
                // Sort trips by dateTime in ascending order (soonest first)
                trips.sortBy { it.dateTime }
                
                // Preload all user badges
                loadUserBadges(driverUids)
                
                tripsAdapter.notifyDataSetChanged()
                
                if (trips.isEmpty()) {
                    rvTrips.visibility = View.GONE
                    tvEmptyState.visibility = View.VISIBLE
                } else {
                    rvTrips.visibility = View.VISIBLE
                    tvEmptyState.visibility = View.GONE
                }
            }
            .addOnFailureListener { e ->
                if (isAdded && context != null) {
                    Toast.makeText(context, "Failed to load trips: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
    
    private fun loadUserBadges(driverUids: Set<String>) {
        // Check if fragment is added
        if (!isAdded) return
        
        for (uid in driverUids) {
            // Skip if already cached
            if (userBadgesCache.containsKey(uid)) continue
            
            firestore.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (!isAdded) return@addOnSuccessListener
                    
                    if (document.exists()) {
                        val badges = mutableListOf<String>()
                        
                        val hall = document.getString("hallResident")
                        if (!hall.isNullOrEmpty()) {
                            badges.add("🏠 $hall")
                        }
                        
                        val club = document.getString("clubMember")
                        if (!club.isNullOrEmpty()) {
                            badges.add("👥 $club")
                        }
                        
                        val cohort = document.getString("courseCohort")
                        if (!cohort.isNullOrEmpty()) {
                            badges.add("🎓 $cohort")
                        }
                        
                        userBadgesCache[uid] = if (badges.isNotEmpty()) {
                            badges.joinToString(" • ")
                        } else {
                            ""
                        }
                    } else {
                        userBadgesCache[uid] = ""
                    }
                    
                    // Notify adapter to update
                    tripsAdapter.notifyDataSetChanged()
                }
                .addOnFailureListener {
                    // Cache empty string on failure
                    userBadgesCache[uid] = ""
                }
        }
    }
    
    private fun openCheckoutDialog(trip: Trip) {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        
        if (currentUser == null) {
            Toast.makeText(context, "Please login to book a ride", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Check if user is trying to book their own trip
        if (currentUser.uid == trip.driverUid) {
            Toast.makeText(context, "You cannot book your own trip", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Create custom dialog view
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_checkout, null)
        
        val tvTripSummary = dialogView.findViewById<TextView>(R.id.tvTripSummary)
        val tvDriverInfo = dialogView.findViewById<TextView>(R.id.tvDriverInfo)
        val tvPriceBreakdown = dialogView.findViewById<TextView>(R.id.tvPriceBreakdown)
        val rgPaymentMethod = dialogView.findViewById<RadioGroup>(R.id.rgPaymentMethod)
        val btnConfirmBooking = dialogView.findViewById<Button>(R.id.btnConfirmBooking)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
        
        // Format trip details
        val dateFormat = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
        val tripDate = if (trip.dateTime > 0) dateFormat.format(Date(trip.dateTime)) else "Date not set"
        
        tvTripSummary.text = "${trip.origin} → ${trip.destination}\n$tripDate"
        tvDriverInfo.text = "Driver: ${trip.driverName}"
        tvPriceBreakdown.text = String.format("Price per seat: $%.2f\nTotal: $%.2f", trip.pricePerSeat, trip.pricePerSeat)
        
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Checkout")
            .setView(dialogView)
            .create()
        
        btnConfirmBooking.setOnClickListener {
            val selectedPaymentId = rgPaymentMethod.checkedRadioButtonId
            if (selectedPaymentId == -1) {
                Toast.makeText(context, "Please select a payment method", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val paymentMethod = when (selectedPaymentId) {
                R.id.rbCash -> "cash"
                R.id.rbPayNow -> "paynow"
                R.id.rbCard -> "card"
                else -> "cash"
            }
            
            // Disable button to prevent double clicks
            btnConfirmBooking.isEnabled = false
            
            // Create booking
            processBooking(trip, currentUser.uid, paymentMethod, dialog, btnConfirmBooking)
        }
        
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        
        dialog.show()
    }
    
    private fun processBooking(trip: Trip, passengerUid: String, paymentMethod: String, dialog: AlertDialog, button: Button) {
        // Get passenger name from Firestore
        firestore.collection("users")
            .document(passengerUid)
            .get()
            .addOnSuccessListener { document ->
                val passengerName = document.getString("fullName") ?: "Unknown"
                
                // Create booking object
                val bookingId = firestore.collection("bookings").document().id
                val booking = Booking(
                    bookingId = bookingId,
                    tripId = trip.tripId,
                    passengerUid = passengerUid,
                    passengerName = passengerName,
                    driverUid = trip.driverUid,
                    driverName = trip.driverName,
                    origin = trip.origin,
                    destination = trip.destination,
                    dateTime = trip.dateTime,
                    seatsBooked = 1,
                    totalPrice = trip.pricePerSeat,
                    paymentMethod = paymentMethod,
                    bookingStatus = "confirmed"
                )
                
                // Save booking to Firestore
                firestore.collection("bookings")
                    .document(bookingId)
                    .set(booking)
                    .addOnSuccessListener {
                        // Update trip with new passenger
                        val updatedPassengers = trip.passengers.toMutableList()
                        updatedPassengers.add(passengerUid)
                        
                        firestore.collection("trips")
                            .document(trip.tripId)
                            .update(
                                mapOf(
                                    "passengers" to updatedPassengers,
                                    "bookedSeats" to trip.bookedSeats + 1,
                                    "seatsAvailable" to trip.seatsAvailable - 1
                                )
                            )
                            .addOnSuccessListener {
                                if (isAdded && context != null) {
                                    Toast.makeText(context, "Booking confirmed! Check History for details.", Toast.LENGTH_LONG).show()
                                    dialog.dismiss()
                                    // Reload trips to show updated availability
                                    loadTrips()
                                }
                            }
                            .addOnFailureListener { e ->
                                if (isAdded && context != null) {
                                    Toast.makeText(context, "Failed to update trip: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                                button.isEnabled = true
                            }
                    }
                    .addOnFailureListener { e ->
                        if (isAdded && context != null) {
                            Toast.makeText(context, "Failed to create booking: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                        button.isEnabled = true
                    }
            }
            .addOnFailureListener { e ->
                if (isAdded && context != null) {
                    Toast.makeText(context, "Failed to get user info: ${e.message}", Toast.LENGTH_SHORT).show()
                }
                button.isEnabled = true
            }
    }
    
    // Simple adapter for trips
    inner class TripsAdapter(
        private val trips: List<Trip>,
        private val userBadgesCache: Map<String, String>
    ) : RecyclerView.Adapter<TripsAdapter.TripViewHolder>() {
        
        inner class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvDriverName: TextView = itemView.findViewById(R.id.tvDriverName)
            val tvDriverBadges: TextView = itemView.findViewById(R.id.tvDriverBadges)
            val tvDateTime: TextView = itemView.findViewById(R.id.tvDateTime)
            val tvSeats: TextView = itemView.findViewById(R.id.tvSeats)
            val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
            val tvOrigin: TextView = itemView.findViewById(R.id.tvOrigin)
            val tvDestination: TextView = itemView.findViewById(R.id.tvDestination)
            val tvNoSmoking: TextView = itemView.findViewById(R.id.tvNoSmoking)
            val tvNoPets: TextView = itemView.findViewById(R.id.tvNoPets)
            val tvMusic: TextView = itemView.findViewById(R.id.tvMusic)
            val tvQuietRide: TextView = itemView.findViewById(R.id.tvQuietRide)
            val btnBookTrip: Button = itemView.findViewById(R.id.btnBookTrip)
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_trip, parent, false)
            return TripViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
            val trip = trips[position]
            
            try {
                val dateFormat = SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault())
                val dateStr = if (trip.dateTime > 0) {
                    dateFormat.format(Date(trip.dateTime))
                } else {
                    "Date not set"
                }
                
                holder.tvDriverName.text = trip.driverName.ifEmpty { "Unknown Driver" }
                holder.tvDateTime.text = dateStr
                val availableSeats = trip.seatsAvailable - trip.bookedSeats
                holder.tvSeats.text = "$availableSeats seat${if (availableSeats != 1) "s" else ""}"
                holder.tvPrice.text = String.format("$%.2f/seat", trip.pricePerSeat)
                holder.tvOrigin.text = trip.origin.ifEmpty { "Not specified" }
                holder.tvDestination.text = trip.destination.ifEmpty { "Not specified" }
                
                // Display cached badges
                val badges = userBadgesCache[trip.driverUid] ?: ""
                if (badges.isNotEmpty()) {
                    holder.tvDriverBadges.text = badges
                    holder.tvDriverBadges.visibility = View.VISIBLE
                } else {
                    holder.tvDriverBadges.visibility = View.GONE
                }
                
                // Show/hide preference chips
                holder.tvNoSmoking.visibility = if (trip.noSmoking) View.VISIBLE else View.GONE
                holder.tvNoPets.visibility = if (trip.noPets) View.VISIBLE else View.GONE
                holder.tvMusic.visibility = if (trip.musicAllowed) View.VISIBLE else View.GONE
                holder.tvQuietRide.visibility = if (trip.quietRide) View.VISIBLE else View.GONE
                
                // Handle book button click
                if (availableSeats <= 0) {
                    holder.btnBookTrip.isEnabled = false
                    holder.btnBookTrip.text = "Fully Booked"
                } else {
                    holder.btnBookTrip.isEnabled = true
                    holder.btnBookTrip.text = "Book Ride"
                    holder.btnBookTrip.setOnClickListener {
                        if (isAdded && context != null) {
                            openCheckoutDialog(trip)
                        }
                    }
                }
            } catch (e: Exception) {
                // Handle any errors gracefully
                holder.tvDriverName.text = "Error loading trip"
                holder.tvDriverBadges.visibility = View.GONE
            }
        }
        
        override fun getItemCount() = trips.size
    }
}
