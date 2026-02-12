package com.example.logisticshowcase.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Message")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String,
    val type: Int,
    val deliveryId: Int,
    val orderId: Int,
    val clientId: Int,
    val date: Long
) {
}