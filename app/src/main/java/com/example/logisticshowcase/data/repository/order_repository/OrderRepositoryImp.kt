package com.example.logisticshowcase.data.repository.order_repository

import com.example.logisticshowcase.data.db.AppDatabase
import com.example.logisticshowcase.data.db.entity.ClientEntity
import com.example.logisticshowcase.data.db.entity.OrderEntity
import com.example.logisticshowcase.data.db.entity.OrderItemEntity
import com.example.logisticshowcase.data.firebase.FirestoreDataSource
import com.example.logisticshowcase.data.firebase.collection.ClientCollection
import com.example.logisticshowcase.data.firebase.collection.OrderCollection
import com.example.logisticshowcase.data.firebase.collection.OrderItemCollection
import kotlinx.coroutines.flow.Flow

class OrderRepositoryImp(
    private val firestore: FirestoreDataSource,
    private val appDatabase: AppDatabase
): OrderRepository  {
    override suspend fun getRemoteOrders(): Result<List<OrderCollection>> {
        return firestore.getOrders()
    }

    override suspend fun getRemoteOrderItems(): Result<List<OrderItemCollection>> {
        return firestore.getOrderItems()
    }

    override suspend fun getClient(): Result<ClientCollection> {
        return firestore.getClientById("372vZ9Pa4oH4kRkAZcYU")
    }

    override suspend fun localSaveOrder(order: OrderEntity) {
        appDatabase.orderDao().insert(order)
    }

    override suspend fun localSaveOrderItem(orderItem: OrderItemEntity) {
        appDatabase.orderItemDao().insert(orderItem)
    }

    override suspend fun localSaveClient(client: ClientEntity) {
        appDatabase.clientDao().insert(client)
    }

    override fun getOrderFlow(): Flow<OrderEntity> {
        return appDatabase.orderDao().getOrderByIdFlow(1)
    }

    override fun getOrderItemFlow(): Flow<List<OrderItemEntity>> {
        return appDatabase.orderItemDao().getOrderItemsByOrderIdFlow(0)
    }

    override fun getClientFlow(): Flow<ClientEntity> {
        return appDatabase.clientDao().getClientById(1)
    }
}