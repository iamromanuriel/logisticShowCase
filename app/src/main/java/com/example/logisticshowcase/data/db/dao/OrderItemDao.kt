package com.example.logisticshowcase.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.logisticshowcase.data.db.BaseDao
import com.example.logisticshowcase.data.db.entity.OrderItemEntity

@Dao
interface OrderItemDao{

    @Query("SELECT * FROM OrderItem WHERE orderId = :orderId")
    fun getOrderItemsByOrderId(orderId: Int): List<OrderItemEntity>
}


