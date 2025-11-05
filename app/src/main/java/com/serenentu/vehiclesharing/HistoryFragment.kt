package com.serenentu.vehiclesharing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.serenentu.vehiclesharing.data.model.Trip
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : Fragment() {
    
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private val trips = mutableListOf<Trip>()
    private lateinit var adapter: TripsAdapter
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        // For now, we'll reuse the fragment_history.xml but we need to add RecyclerView
        // Let's check if there's a way to update the layout or use a TextView for now
        
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
    
    private fun loadUserTrips(userId: String) {
        firestore.collection("trips")
            .whereEqualTo("driverUid", userId)
            .orderBy("dateTime", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                trips.clear()
                for (document in documents) {
                    val trip = document.toObject(Trip::class.java)
                    trips.add(trip)
                }
                
                // Display trips in the TextView for now
                val tvTripList = view?.findViewById<TextView>(android.R.id.text1)
                if (trips.isEmpty()) {
                    tvTripList?.text = "No trips found. Post your first trip!"
                } else {
                    val dateFormat = SimpleDateFormat("MMM dd, hh:mm a", Locale.getDefault())
                    val tripTexts = trips.map { trip ->
                        val dateStr = dateFormat.format(Date(trip.dateTime))
                        "${trip.origin} → ${trip.destination}\n$dateStr • ${trip.seatsAvailable} seat(s) • ${trip.status}"
                    }
                    tvTripList?.text = tripTexts.joinToString("\n\n")
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to load trips: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    
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
            
            holder.tvTripInfo.text = """
                ${trip.origin} → ${trip.destination}
                $dateStr • ${trip.seatsAvailable} seat(s)
                Status: ${trip.status}
            """.trimIndent()
        }
        
        override fun getItemCount() = trips.size
    }
}
