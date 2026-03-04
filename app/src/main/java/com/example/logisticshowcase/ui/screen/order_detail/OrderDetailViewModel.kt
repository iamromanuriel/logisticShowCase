package com.example.logisticshowcase.ui.screen.order_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logisticshowcase.data.deliveryManager.DeliveryManager
import com.example.logisticshowcase.data.deliveryManager.DeliveryState
import com.example.logisticshowcase.data.model.OrderDetail
import com.example.logisticshowcase.data.model.ProductOrder
import com.example.logisticshowcase.data.repository.main_repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


data class OrderDetailState(
    val orderDetail  : OrderDetail? = null,
    val products: List<ProductOrder> = emptyList(),
    val isLoading : Boolean = false,
    val deliveryState: DeliveryState? = null
)
@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val deliveryManager: DeliveryManager
): ViewModel() {
    private val _state = MutableStateFlow<OrderDetailState>(OrderDetailState())
    val state = combine(
        _state,
        mainRepository.getOrderDetail(),
        mainRepository.getProductOrder(),
        deliveryManager.deliveryState.distinctUntilChanged { old, new -> old == new }
    ){ state, orderDetail, products, deliveryState ->
        state.copy(
            orderDetail = orderDetail,
            products = products,
            deliveryState = deliveryState
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        OrderDetailState()
    )


}