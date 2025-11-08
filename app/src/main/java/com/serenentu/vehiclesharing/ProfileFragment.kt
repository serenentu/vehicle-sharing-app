package com.serenentu.vehiclesharing

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.serenentu.vehiclesharing.data.NTULocations
import com.serenentu.vehiclesharing.data.model.User

class ProfileFragment : Fragment() {
    
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        val tvUserName = view.findViewById<TextView>(R.id.tvUserName)
        val tvUserEmail = view.findViewById<TextView>(R.id.tvUserEmail)
        val rbNoPreference = view.findViewById<RadioButton>(R.id.rbNoPreference)
        val rbSameGender = view.findViewById<RadioButton>(R.id.rbSameGender)
        val cbNoSmokingProfile = view.findViewById<CheckBox>(R.id.cbNoSmokingProfile)
        val cbNoPetsProfile = view.findViewById<CheckBox>(R.id.cbNoPetsProfile)
        val cbMusicProfile = view.findViewById<CheckBox>(R.id.cbMusicProfile)
        val cbQuietRideProfile = view.findViewById<CheckBox>(R.id.cbQuietRideProfile)
        val etHallResident = view.findViewById<AutoCompleteTextView>(R.id.etHallResident)
        val etClubMember = view.findViewById<EditText>(R.id.etClubMember)
        val etCourseCohort = view.findViewById<AutoCompleteTextView>(R.id.etCourseCohort)
        val btnSaveProfile = view.findViewById<Button>(R.id.btnSaveProfile)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        val btnEmergencyCall = view.findViewById<Button>(R.id.btnEmergencyCall)
        
        // Setup dropdown adapters
        val hallAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            listOf("") + NTULocations.HALLS
        )
        etHallResident.setAdapter(hallAdapter)
        
        val cohortAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            listOf("") + NTULocations.COURSE_COHORTS
        )
        etCourseCohort.setAdapter(cohortAdapter)
        
        val currentUser = auth.currentUser
        if (currentUser == null) {
            // User not logged in, navigate to welcome
            findNavController().navigate(R.id.action_profileFragment_to_welcomeFragment)
            return view
        }
        
        // Load user profile
        firestore.collection("users")
            .document(currentUser.uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(User::class.java)
                    if (user != null) {
                        tvUserName.text = user.fullName
                        tvUserEmail.text = user.email
                        
                        // Set preferences
                        when (user.genderPreference) {
                            "no_preference" -> rbNoPreference.isChecked = true
                            "same_gender" -> rbSameGender.isChecked = true
                        }
                        
                        cbNoSmokingProfile.isChecked = user.noSmokingPreference
                        cbNoPetsProfile.isChecked = user.noPetsPreference
                        cbMusicProfile.isChecked = user.musicPreference
                        cbQuietRideProfile.isChecked = user.quietRidePreference
                        
                        // Set NTU badges
                        etHallResident.setText(user.hallResident, false)
                        etClubMember.setText(user.clubMember)
                        etCourseCohort.setText(user.courseCohort, false)
                    }
                } else {
                    // Profile doesn't exist, show basic info
                    tvUserName.text = currentUser.displayName ?: "User"
                    tvUserEmail.text = currentUser.email ?: ""
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to load profile: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        
        btnSaveProfile.setOnClickListener {
            val genderPreference = if (rbSameGender.isChecked) "same_gender" else "no_preference"
            
            val updates = hashMapOf<String, Any>(
                "genderPreference" to genderPreference,
                "noSmokingPreference" to cbNoSmokingProfile.isChecked,
                "noPetsPreference" to cbNoPetsProfile.isChecked,
                "musicPreference" to cbMusicProfile.isChecked,
                "quietRidePreference" to cbQuietRideProfile.isChecked,
                "hallResident" to etHallResident.text.toString().trim(),
                "clubMember" to etClubMember.text.toString().trim(),
                "courseCohort" to etCourseCohort.text.toString().trim()
            )
            
            btnSaveProfile.isEnabled = false
            
            firestore.collection("users")
                .document(currentUser.uid)
                .update(updates)
                .addOnSuccessListener {
                    btnSaveProfile.isEnabled = true
                    Toast.makeText(context, "Preferences saved successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    btnSaveProfile.isEnabled = true
                    Toast.makeText(context, "Failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
        
        btnEmergencyCall.setOnClickListener {
            // Open dialer with NTU security hotline
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${NTULocations.NTU_SECURITY_HOTLINE}")
            }
            startActivity(intent)
        }
        
        btnLogout.setOnClickListener {
            auth.signOut()
            Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_profileFragment_to_welcomeFragment)
        }
        
        return view
    }
}
