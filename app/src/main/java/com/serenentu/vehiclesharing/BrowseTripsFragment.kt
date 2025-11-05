package com.serenentu.vehiclesharing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.serenentu.vehiclesharing.data.model.Trip
import java.text.SimpleDateFormat
import java.util.*

class BrowseTripsFragment : Fragment() {
    
    private lateinit var firestore: FirebaseFirestore
    private lateinit var rvTrips: RecyclerView
    private lateinit var tvEmptyState: TextView
    private lateinit var tripsAdapter: TripsAdapter
    private val trips = mutableListOf<Trip>()
    
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
        
        // Setup RecyclerView
        tripsAdapter = TripsAdapter(trips)
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
                cbFilterMusic.isChecked
            )
            filterPanel.visibility = View.GONE
        }
        
        // Load all trips initially
        loadTrips()
        
        return view
    }
    
    private fun loadTrips(
        origin: String = "",
        destination: String = "",
        noSmoking: Boolean = false,
        noPets: Boolean = false,
        musicAllowed: Boolean = false
    ) {
        var query: Query = firestore.collection("trips")
            .whereEqualTo("status", "active")
            .orderBy("dateTime", Query.Direction.ASCENDING)
        
        // Note: Firestore has limitations on compound queries
        // For more complex filtering, we'll filter in memory
        query.get()
            .addOnSuccessListener { documents ->
                trips.clear()
                
                for (document in documents) {
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
                    
                    if (matches) {
                        trips.add(trip)
                    }
                }
                
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
                Toast.makeText(context, "Failed to load trips: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    
    // Simple adapter for trips
    inner class TripsAdapter(private val trips: List<Trip>) : 
        RecyclerView.Adapter<TripsAdapter.TripViewHolder>() {
        
        inner class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvTripInfo: TextView = itemView.findViewById(android.R.id.text1)
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
            return TripViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
            val trip = trips[position]
            val dateFormat = SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault())
            val dateStr = dateFormat.format(Date(trip.dateTime))
            
            val preferences = mutableListOf<String>()
            if (trip.noSmoking) preferences.add("No Smoking")
            if (trip.noPets) preferences.add("No Pets")
            if (trip.musicAllowed) preferences.add("Music OK")
            
            val prefStr = if (preferences.isNotEmpty()) {
                "\n${preferences.joinToString(", ")}"
            } else {
                ""
            }
            
            holder.tvTripInfo.text = """
                ${trip.origin} → ${trip.destination}
                ${trip.driverName} • $dateStr
                ${trip.seatsAvailable} seat(s) available$prefStr
            """.trimIndent()
        }
        
        override fun getItemCount() = trips.size
    }
}
