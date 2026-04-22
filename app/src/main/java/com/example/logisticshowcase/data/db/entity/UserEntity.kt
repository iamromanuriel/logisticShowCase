package com.example.logisticshowcase.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var idDocument: String? = null,
    var name: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    val enable: Boolean ? = false,
    val role: String ? = null,
    val vehicleType: Int ? = null
)
