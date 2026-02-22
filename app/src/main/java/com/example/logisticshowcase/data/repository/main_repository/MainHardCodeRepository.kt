package com.example.logisticshowcase.data.repository.main_repository

import com.example.logisticshowcase.data.model.OrderDetail
import com.example.logisticshowcase.data.model.OrderItem
import com.example.logisticshowcase.data.model.ProductOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MainHardCodeRepository : MainRepository {
    private val orders = listOf(
        OrderItem(
            id = 1,
            clientName = "Roman Uriel Francisco",
            addressName = "18 Poniente 1504, El Tamborcito, Puebla",
            itemCount = 4,
            latitude = 19.062185,
            longitude = -98.2314209
        ),
        OrderItem(
            id = 2,
            clientName = "Taqueria El Perico",
            addressName = "Reforma 5200, Aquiles Serdan, Puebla",
            itemCount = 12,
            latitude = 19.060238,
            longitude = -98.2276122
        ),
        OrderItem(
            id = 3,
            clientName = "Estacionamiento San Martin Reforma",
            addressName = "Av. Reforma 1906, San Martin, ",
            itemCount = 12,
            latitude = 19.051542,
            longitude = -98.212,
        )
    )


    private val orderDetail = OrderDetail(
        id = 1,
        clientName = "Roman Uriel Francisco",
        addressName = "Diagonal reforma 5315, Aquiles Serdan, Puebla",
        status = 1,
        reference = "A lado de un Oxxo, Xd",
        itemCount = 3,
        latitude = 19.4326,
        longitude = -99.1332,
        totalPrice = 98.0,
    )


    private val products = listOf(
        ProductOrder(
            id = 1,
            name = "Garrafon 10 litros, Ciel",
            description = "Color azul, nombre del cliente ",
            quantity = 2,
            price = 10.0
        ),
        ProductOrder(
            id = 2,
            name = "Garrafon 20 Bonafont",
            description = "Color rosa/rojo",
            quantity = 2,
            price = 20.0
        ),
        ProductOrder(
            id = 3,
            name = "Garrafon 20 Bonafont, Promium",
            description = "Color rosa/rojo",
            quantity = 1,
            price = 20.0
        )
    )

    override  fun getOrders(): Flow<List<OrderItem>> {
        return flowOf(orders)
    }

    override fun getOrderDetail(): Flow<OrderDetail> {
        return flowOf(orderDetail)
    }

    override fun getProductOrder(): Flow<List<ProductOrder>> {
        return flowOf(products)
    }


}