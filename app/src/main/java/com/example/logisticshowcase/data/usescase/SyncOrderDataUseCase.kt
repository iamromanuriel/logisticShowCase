package com.example.logisticshowcase.data.usescase

import com.example.logisticshowcase.data.db.entity.ClientEntity
import com.example.logisticshowcase.data.db.entity.OrderEntity
import com.example.logisticshowcase.data.db.entity.OrderItemEntity
import com.example.logisticshowcase.data.firebase.collection.OrderItemCollection
import com.example.logisticshowcase.data.repository.order_repository.OrderRepository
import com.example.logisticshowcase.data.repository.order_repository.OrderRepositoryImp
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class SyncOrderDataUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
){

    suspend fun invoke(): Result<Unit> {
        return coroutineScope {
            runCatching {
                val orderDeferred = async { orderRepository.getRemoteOrders() }
                val orderItemDeferred = async { orderRepository.getRemoteOrderItems() }
                val clientDeferred = async { orderRepository.getClient() }

                val orderCollection = orderDeferred.await()
                val orderItemCollection = orderItemDeferred.await()
                val clientCollection = clientDeferred.await()

                val orders = orderCollection.getOrNull()
                val orderItems = orderItemCollection.getOrNull()
                val client = clientCollection.getOrNull()

                orders?.forEach { order ->
                    val entityOrder = OrderEntity(
                        orderNum = order.orderNum,
                        amount = order.amount,
                        dateCreate = order.dateCreate?.time?:0,
                        dateStart = order.dateStart?.time?: 0,
                        dateEnd = order.dateEnd?.time?:0,
                        status = order.status,
                        clientId = order.clientId,
                        driverId = order.driverId
                    )
                    orderRepository.localSaveOrder(entityOrder)
                }

                orderItems?.forEach { orderItem ->
                    val orderItemEntity = OrderItemEntity(
                        amount = orderItem.amount,
                        name = orderItem.name,
                        prince = orderItem.price,
                        orderId = orderItem.orderId,
                        idOrder = ""
                    )
                    orderRepository.localSaveOrderItem(orderItemEntity)
                }

                val customer = ClientEntity(
                    name = client?.name?: "",
                    email = client?.email?: "",
                    phone = client?.phone?: "",
                    address = client?.address?: "",
                    latitude = client?.latitude?: 0.0,
                    longitude = client?.longitude?: 0.0
                )
                orderRepository.localSaveClient(client = customer)

                Unit
            }

        }
    }
}