package com.serenentu.vehiclesharing.data.model

data class Trip(
    val tripId: String = "",
    val driverUid: String = "",
    val driverName: String = "",
    val origin: String = "",
    val destination: String = "",
    val dateTime: Long = 0L,
    val seatsAvailable: Int = 0,
    val noSmoking: Boolean = false,
    val noPets: Boolean = false,
    val musicAllowed: Boolean = false,
    val quietRide: Boolean = false, // New: quiet ride preference
    val additionalNotes: String = "",
    val status: String = "active", // active, completed, cancelled
    val passengers: List<String> = emptyList() // List of passenger UIDs
)
