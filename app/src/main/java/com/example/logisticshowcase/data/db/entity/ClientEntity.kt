package com.example.logisticshowcase.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Client")
data class ClientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)