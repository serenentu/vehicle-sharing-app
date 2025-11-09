package com.serenentu.vehiclesharing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {
    
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        val tvWelcome = view.findViewById<TextView>(R.id.tvWelcomeHome)
        val btnGiveRide = view.findViewById<Button>(R.id.btnGiveRide)
        val btnGetRide = view.findViewById<Button>(R.id.btnGetRide)
        
        // Get user name
        val currentUser = auth.currentUser
        if (currentUser != null) {
            firestore.collection("users")
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    val userName = document.getString("fullName") ?: "User"
                    val firstName = userName.split(" ").firstOrNull() ?: userName
                    tvWelcome.text = "Welcome, $firstName!"
                }
        }
        
        // Navigate to Post Trip (Give a Ride)
        btnGiveRide.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_postTripFragment)
        }
        
        // Navigate to Browse Trips (Get a Ride)
        btnGetRide.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_browseTripsFragment)
        }
        
        return view
    }
}
