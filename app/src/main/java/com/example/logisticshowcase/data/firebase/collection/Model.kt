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
) {
}
