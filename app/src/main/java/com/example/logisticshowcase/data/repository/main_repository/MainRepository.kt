package com.example.logisticshowcase.data.repository.main_repository

import com.example.logisticshowcase.data.model.OrderDetail
import com.example.logisticshowcase.data.model.OrderItem
import com.example.logisticshowcase.data.model.ProductOrder
import kotlinx.coroutines.flow.Flow

interface MainRepository {
     fun getOrders(): Flow<List<OrderItem>>
    fun getOrderDetail(): Flow<OrderDetail>
    fun getProductOrder(): Flow<List<ProductOrder>>
}
