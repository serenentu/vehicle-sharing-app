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
        val cbFilterQuietRide = view.findViewById<CheckBox>(R.id.cbFilterQuietRide)
        
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
                cbFilterMusic.isChecked,
                cbFilterQuietRide.isChecked
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
        musicAllowed: Boolean = false,
        quietRide: Boolean = false
    ) {
        firestore.collection("trips")
            .whereEqualTo("status", "active")
            .get()
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
                    
                    if (quietRide && !trip.quietRide) {
                        matches = false
                    }
                    
                    if (matches) {
                        trips.add(trip)
                    }
                }
                
                // Sort trips by dateTime in ascending order (soonest first)
                trips.sortBy { it.dateTime }
                
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
            val tvDriverName: TextView = itemView.findViewById(R.id.tvDriverName)
            val tvDateTime: TextView = itemView.findViewById(R.id.tvDateTime)
            val tvSeats: TextView = itemView.findViewById(R.id.tvSeats)
            val tvOrigin: TextView = itemView.findViewById(R.id.tvOrigin)
            val tvDestination: TextView = itemView.findViewById(R.id.tvDestination)
            val tvNoSmoking: TextView = itemView.findViewById(R.id.tvNoSmoking)
            val tvNoPets: TextView = itemView.findViewById(R.id.tvNoPets)
            val tvMusic: TextView = itemView.findViewById(R.id.tvMusic)
            val tvQuietRide: TextView = itemView.findViewById(R.id.tvQuietRide)
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_trip, parent, false)
            return TripViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
            val trip = trips[position]
            val dateFormat = SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault())
            val dateStr = dateFormat.format(Date(trip.dateTime))
            
            holder.tvDriverName.text = trip.driverName
            holder.tvDateTime.text = dateStr
            holder.tvSeats.text = "${trip.seatsAvailable} seat${if (trip.seatsAvailable != 1) "s" else ""}"
            holder.tvOrigin.text = trip.origin
            holder.tvDestination.text = trip.destination
            
            // Show/hide preference chips
            holder.tvNoSmoking.visibility = if (trip.noSmoking) View.VISIBLE else View.GONE
            holder.tvNoPets.visibility = if (trip.noPets) View.VISIBLE else View.GONE
            holder.tvMusic.visibility = if (trip.musicAllowed) View.VISIBLE else View.GONE
            holder.tvQuietRide.visibility = if (trip.quietRide) View.VISIBLE else View.GONE
        }
        
        override fun getItemCount() = trips.size
    }
}
