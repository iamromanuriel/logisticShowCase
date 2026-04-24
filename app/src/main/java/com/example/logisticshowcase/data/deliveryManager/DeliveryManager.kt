package com.example.logisticshowcase.data.deliveryManager

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
data class TimelineStep(
    val title: String,
    val subtitle: String,
    val action: String? = null,
    val isActive: Boolean,
    val isCompleted: Boolean
)

fun getGenericTimeline(currentState: DeliveryState): List<TimelineStep> {
    val states = listOf(
        Triple("Asignado", "Esperando inicio de ruta", DeliveryState.Idle),
        Triple("En Tránsito", "Viajando al destino", DeliveryState.InTransit),
        Triple("En Destino", "Confirmando llegada", DeliveryState.ArrivedAtDestination),
        Triple("Finalizado", "Proceso concluido", DeliveryState.Completed)
    )

    return states.map { (title, subtitle, state) ->
        TimelineStep(
            title = title,
            subtitle = subtitle,
            isCompleted = currentState.order > state.order || currentState is DeliveryState.Completed,
            isActive = currentState.order == state.order && currentState !is DeliveryState.Cancelled
        )
    }
}

sealed class DeliveryState(val order: Int, val label: String) {
    data object Idle : DeliveryState(0, "Iniciar ruta")
    data object InTransit : DeliveryState(1, "Llege")
    data object ArrivedAtDestination : DeliveryState(2, "Finalizar")
    data object Completed : DeliveryState(3, "")
    data object Cancelled : DeliveryState(4, "")
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

