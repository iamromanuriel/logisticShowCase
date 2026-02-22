package com.example.logisticshowcase.data.model

import com.google.android.gms.maps.model.LatLng

data class OrderItem(
    val id: Int,
    val clientName: String,
    val addressName: String,
    val itemCount: Int,
    val latitude: Double,
    val longitude: Double,
){
    fun toLatLng() = LatLng(latitude, longitude)
}

data class OrderDetail(
    val id: Int,
    val clientName: String,
    val addressName: String,
    val status: Int,
    val reference: String,
    val itemCount: Int,
    val latitude: Double,
    val longitude: Double,
    val totalPrice: Double
)

data class ProductOrder(
    val id: Int,
    val name: String,
    val description: String,
    val quantity: Int,
    val price: Double
)