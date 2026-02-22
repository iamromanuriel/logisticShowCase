package com.example.logisticshowcase.ui.screen.map_deliver


import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logisticshowcase.util.location.LocationUtil
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@Immutable
data class MapDeliveryState(
    val location: LatLng = LatLng(19.0437, -98.1983),
    val isLoading: Boolean = false
)

sealed class MapDeliveryIntent{
    data class OnSelectOrder(val idOrder: Int): MapDeliveryIntent()

}

@HiltViewModel
class MapDeliveryViewModel @Inject constructor(
    private val locationUtil: LocationUtil
): ViewModel() {

    private val _state = MutableStateFlow(MapDeliveryState())
    val state = _state.asStateFlow()

    init {
        getLocation()
    }


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
}