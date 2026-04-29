package com.example.logisticshowcase.ui.screen.order_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logisticshowcase.data.db.entity.ClientEntity
import com.example.logisticshowcase.data.db.entity.OrderEntity
import com.example.logisticshowcase.data.db.entity.OrderItemEntity
import com.example.logisticshowcase.data.deliveryManager.DeliveryManager
import com.example.logisticshowcase.data.deliveryManager.DeliveryState
import com.example.logisticshowcase.data.repository.order_repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


data class OrderDetailState(
    val orderDetail  : OrderEntity? = null,
    val products: List<OrderItemEntity> = emptyList(),
    val client: ClientEntity? = null,
    val isLoading : Boolean = false,
    val deliveryState: DeliveryState? = null
)

sealed class OrderDetailIntent(){
    data class OnChangeState(val newState: Int): OrderDetailIntent()
}

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val deliveryManager: DeliveryManager
): ViewModel() {

    private val _message = MutableSharedFlow<String?>()
    val message = _message.asSharedFlow()
    private val _state = MutableStateFlow(OrderDetailState())
    val state = combine(
        _state,
        orderRepository.getOrderFlow(),
        orderRepository.getOrderItemFlow(),
        orderRepository.getClientFlow(),
        deliveryManager.deliveryState
    ){ state, order, orderItem, client, deliveryState ->
        state.copy(
            orderDetail = order,
            products = orderItem,
            client = client,
            deliveryState = deliveryState
        )
    }
        .onStart { onInit() }
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OrderDetailState()
    )

    private fun onInit() {

        viewModelScope.launch {
            orderRepository.getOrderFlow()
                .map { it.status }
                .distinctUntilChanged()
                .collect { remoteStatus ->
                    deliveryManager.syncStateFromRemote(remoteStatus)
                        .onFailure { _message.emit(it.message) }
                }
        }
    }




    fun onIntent(newState: OrderDetailIntent){
        when(newState){
            is OrderDetailIntent.OnChangeState -> onChangeState(newState.newState)
        }
    }

    private fun onChangeState(newState: Int){
        val oldState = _state.value.deliveryState

        viewModelScope.launch {
            deliveryManager
                .updateDeliveryState(newState)
                .onSuccess {
                    _message.emit("Cambio de estado exitoso")
                }.onFailure {
                    _message.emit(it.message.toString())
                }
        }

    }
}