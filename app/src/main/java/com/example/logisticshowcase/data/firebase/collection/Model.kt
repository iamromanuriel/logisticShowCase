package com.example.logisticshowcase.data.firebase.collection

import java.util.Date

data class UserCollection(
    val user: String = "",
    val lastName: String = "",
    val createdAt: Date? = null,

) {
}

data class VehicleCollection(
    val brand: String = "",
    val model: String = "",
    val year: Int = 0,
    val plate: String = "",
    val type: String = "",
    val description: String = ""
)

data class OrderCollection(
    val orderNum: String = "",
    val amount: Double = 0.0,
    val dateCreate: Date? = null,
    val dateStart: Date? = null,
    val dateEnd: Date? = null,
    val status: Int = 0,
    val clientId: Int = 0,
    val driverId: Int = 0,
)

data class ClientCollection(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

data class OrderItemCollection(
    val amount: Double = 0.0,
    val name: String = "",
    val price: Double = 0.0,
    val orderId: Int = 0,
)