package com.serenentu.vehiclesharing.data.model

data class User(
    val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val genderPreference: String = "no_preference", // no_preference, same_gender
    val noSmokingPreference: Boolean = false,
    val noPetsPreference: Boolean = false,
    val musicPreference: Boolean = false,
    val quietRidePreference: Boolean = false, // New: prefer quiet rides
    val rating: Double = 0.0,
    val totalRides: Int = 0,
    // NTU-specific profile badges
    val hallResident: String = "", // e.g., "Hall 3", "Hall 16"
    val clubMember: String = "", // e.g., "IEEE Club", "Sports Club"
    val courseCohort: String = "" // e.g., "EEE Year 4", "CS Year 2"
)
