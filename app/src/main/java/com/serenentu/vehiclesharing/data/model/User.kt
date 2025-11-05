package com.serenentu.vehiclesharing.data.model

data class User(
    val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val genderPreference: String = "no_preference", // no_preference, same_gender
    val noSmokingPreference: Boolean = false,
    val noPetsPreference: Boolean = false,
    val musicPreference: Boolean = false,
    val rating: Double = 0.0,
    val totalRides: Int = 0
)
