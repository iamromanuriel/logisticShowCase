package com.example.logisticshowcase.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "OrderItem")
data class OrderItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val amount: Double,
    val name: String,
    val prince: Int,
    val orderId: Int,
    val barcode: String,
    val idOrder: String
) {
}