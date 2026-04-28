package com.example.logisticshowcase.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.logisticshowcase.data.db.BaseDao
import com.example.logisticshowcase.data.db.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao: BaseDao<OrderEntity>{

    @Query("SELECT * FROM `Order` WHERE id = :id")
    fun getOrderById(id: Int): OrderEntity
    @Query("SELECT * FROM `Order` WHERE id = :id")
    fun getOrderByIdFlow(id: Int): Flow<OrderEntity>
    @Query("SELECT * FROM `Order`")
    fun getOrders(): Flow<List<OrderEntity>>
}