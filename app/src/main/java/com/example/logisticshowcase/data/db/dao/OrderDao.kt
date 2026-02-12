package com.example.logisticshowcase.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.logisticshowcase.data.db.BaseDao
import com.example.logisticshowcase.data.db.entity.OrderEntity

@Dao
interface OrderDao{

    @Query("SELECT * FROM `Order` WHERE id = :id")
    fun getOrderById(id: Int): OrderEntity
}