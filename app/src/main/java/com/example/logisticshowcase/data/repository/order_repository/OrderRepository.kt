package com.example.logisticshowcase.data.repository.order_repository

import com.example.logisticshowcase.data.db.entity.ClientEntity
import com.example.logisticshowcase.data.db.entity.OrderEntity
import com.example.logisticshowcase.data.db.entity.OrderItemEntity
import com.example.logisticshowcase.data.firebase.collection.ClientCollection
import com.example.logisticshowcase.data.firebase.collection.OrderCollection
import com.example.logisticshowcase.data.firebase.collection.OrderItemCollection
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun getRemoteOrders(): Result<List<OrderCollection>>
    suspend fun getRemoteOrderItems(): Result<List<OrderItemCollection>>
    suspend fun getClient(): Result<ClientCollection>
    suspend fun localSaveOrder(order: OrderEntity)
    suspend fun localSaveOrderItem(orderItem: OrderItemEntity)
    suspend fun localSaveClient(client: ClientEntity)

    fun getOrderFlow(): Flow<OrderEntity>
    fun getOrderItemFlow(): Flow<List<OrderItemEntity>>
    fun getClientFlow(): Flow<ClientEntity>
    fun getClientsFlow(): Flow<List<ClientEntity>>
    fun getOrdersFlow(): Flow<List<OrderEntity>>
}