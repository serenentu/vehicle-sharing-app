package com.serenentu.vehiclesharing.data.model

data class Booking(
    val bookingId: String = "",
    val tripId: String = "",
    val passengerUid: String = "",
    val passengerName: String = "",
    val driverUid: String = "",
    val driverName: String = "",
    val origin: String = "",
    val destination: String = "",
    val dateTime: Long = 0L,
    val seatsBooked: Int = 1,
    val totalPrice: Double = 0.0,
    val paymentMethod: String = "cash", // cash, paynow, card
    val bookingStatus: String = "pending", // pending, confirmed, completed, cancelled
    val bookingTime: Long = System.currentTimeMillis()
)
