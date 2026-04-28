package com.example.logisticshowcase.ui.screen.map_deliver


import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logisticshowcase.data.db.entity.ClientEntity
import com.example.logisticshowcase.data.db.entity.OrderEntity
import com.example.logisticshowcase.data.repository.order_repository.OrderRepository
import com.example.logisticshowcase.util.location.LocationUtil
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class MapDeliveryState(
    val location : LatLng = LatLng(0.0, 0.0),
    val clients: List<ClientEntity> = emptyList(),
    val orders: List<OrderEntity> = emptyList(),
    val isLoading: Boolean = false
)

sealed class MapDeliveryIntent{
    data class OnSelectOrder(val idOrder: Int): MapDeliveryIntent()

}

@HiltViewModel
class MapDeliveryViewModel @Inject constructor(
    private val locationUtil: LocationUtil,
    private val orderRepository: OrderRepository
): ViewModel() {

    private val _state = MutableStateFlow(MapDeliveryState())
    val state = combine(
            _state,
            orderRepository.getClientsFlow(),
            orderRepository.getOrdersFlow()
        ){ state, clients, orders ->
            state.copy(
                clients = clients,
                orders = orders
            )
        }
        .onStart { getLocation() }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = MapDeliveryState()
        )


    fun getLocation() {
        viewModelScope.launch {
            locationUtil.getCurrentLocation()
                .onSuccess { locationCurrent ->

                    _state.update { stateCurrent ->
                        stateCurrent.copy(
                            location = LatLng(locationCurrent.latitude, locationCurrent.longitude),
                            isLoading = false
                        )
                    }
                }.onFailure {

                }
        }
    }

    fun onIntent(intent : MapDeliveryIntent) {
        when(intent){
            is MapDeliveryIntent.OnSelectOrder -> onSelectOrder(intent.idOrder)
        }
    }

    private fun onSelectOrder(idOrder: Int){

    }
}