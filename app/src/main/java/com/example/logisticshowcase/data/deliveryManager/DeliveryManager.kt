package com.example.logisticshowcase.data.deliveryManager

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

sealed class DeliveryState{
    object Idle: DeliveryState()
    object InTransit: DeliveryState()
    object ArrivedAtDestination: DeliveryState()
    object Completed: DeliveryState()
    object Cancelled: DeliveryState()
}

interface DeliveryManager{
    val deliveryState : StateFlow<DeliveryState>
    fun updateDeliveryState(newState: DeliveryState): Result<Unit>

    fun cancelDelivery()
}


class DeliveryManagerImpl() : DeliveryManager{
    private val _deliveryState = MutableStateFlow<DeliveryState>(value = DeliveryState.Idle)
    override val deliveryState: StateFlow<DeliveryState>
        get() = _deliveryState

    override fun updateDeliveryState(newState: DeliveryState): Result<Unit> {
        return runCatching {
            val oldState = _deliveryState.value
            if(isValidTransition(old = oldState,new = newState)){
                _deliveryState.value = newState
            }else{
                throw IllegalArgumentException("Invalid transition from $oldState to $newState")
            }
        }
    }

    override fun cancelDelivery() {

    }

    private fun isValidTransition(old: DeliveryState, new: DeliveryState): Boolean{
        return when{
            old == DeliveryState.Idle && new == DeliveryState.InTransit -> true
            old == DeliveryState.InTransit && new == DeliveryState.ArrivedAtDestination -> true
            old == DeliveryState.ArrivedAtDestination && new == DeliveryState.Completed -> true
            else -> false
        }
    }

}

