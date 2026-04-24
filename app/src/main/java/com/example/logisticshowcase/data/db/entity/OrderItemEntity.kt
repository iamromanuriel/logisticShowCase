package com.example.logisticshowcase.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "OrderItem")
data class OrderItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val name: String,
    val prince: Double,
    val orderId: Int,
    val barcode: String? = null,
    val idOrder: String
) {
}