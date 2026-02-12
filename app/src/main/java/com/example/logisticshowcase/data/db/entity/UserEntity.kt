package com.example.logisticshowcase.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val enable: Boolean,
    val role: String,
    val vehicleType: Int
)
