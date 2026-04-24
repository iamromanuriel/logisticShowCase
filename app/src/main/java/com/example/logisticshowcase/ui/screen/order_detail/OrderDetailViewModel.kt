package com.example.logisticshowcase.ui.screen.order_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logisticshowcase.data.db.entity.ClientEntity
import com.example.logisticshowcase.data.db.entity.OrderEntity
import com.example.logisticshowcase.data.db.entity.OrderItemEntity
import com.example.logisticshowcase.data.deliveryManager.DeliveryManager
import com.example.logisticshowcase.data.deliveryManager.DeliveryState
import com.example.logisticshowcase.data.model.OrderDetail
import com.example.logisticshowcase.data.model.ProductOrder
import com.example.logisticshowcase.data.repository.main_repository.MainRepository
import com.example.logisticshowcase.data.repository.order_repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


data class OrderDetailState(
    val orderDetail  : OrderEntity? = null,
    val products: List<OrderItemEntity> = emptyList(),
    val client: ClientEntity? = null,
    val isLoading : Boolean = false,
    val deliveryState: DeliveryState? = null
)

sealed class OrderDetailIntent(){
    data class OnChangeState(val newState: DeliveryState): OrderDetailIntent()
}

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    orderRepository: OrderRepository,
    private val deliveryManager: DeliveryManager
): ViewModel() {
    private val _state = MutableStateFlow(OrderDetailState())
    val state = combine(
        _state,
        orderRepository.getOrderFlow(),
        orderRepository.getOrderItemFlow(),
        orderRepository.getClientFlow()
    ){ state, order, orderItem, client ->
        state.copy(
            orderDetail = order,
            products = orderItem,
            client = client
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OrderDetailState()
    )



    fun onIntent(newState: OrderDetailIntent){
        when(newState){
            is OrderDetailIntent.OnChangeState -> onChangeState(newState.newState)
        }
    }

    private fun onChangeState(newState: DeliveryState){
        deliveryManager.updateDeliveryState(newState)
    }
}