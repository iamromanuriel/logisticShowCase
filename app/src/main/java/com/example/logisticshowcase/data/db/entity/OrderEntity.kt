package com.example.logisticshowcase.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.logisticshowcase.data.deliveryManager.DeliveryState

@Entity(
    tableName = "Order",
)
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val orderNum: String,
    val amount: Double,
    val dateCreate: Long,
    val dateStart: Long,
    val dateEnd: Long,
    val status: Int,
    val clientId: Int,
    val driverId: Int, // User id
) {
    fun toDeliveryState(): DeliveryState{
        return when(status){
            0 -> DeliveryState.Idle
            1 -> DeliveryState.InTransit
            2 -> DeliveryState.ArrivedAtDestination
            3 -> DeliveryState.Completed
            else -> DeliveryState.Idle
        }
    }
}