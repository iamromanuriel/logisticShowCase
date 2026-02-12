package com.example.logisticshowcase.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Order",
)
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val orderNum: String,
    val amount: Double,
    val dateCreate: Long,
    val dateStart: Long,
    val dateEnd: Long,
    val status: Int,
    val clientId: Int,
    val driverId: Int, // User id
) {
}