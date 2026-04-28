package com.example.logisticshowcase.data.deliveryManager

import com.example.logisticshowcase.data.deliveryManager.DeliveryState.Companion.values
import com.example.logisticshowcase.data.deliveryManager.DeliveryState.Idle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.collections.find

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
    data object Cancelled : DeliveryState(4, "");
    companion object {
        val values = listOf(Idle, InTransit, ArrivedAtDestination, Completed, Cancelled)

        fun fromOrder(order: Int): DeliveryState =
            values.firstOrNull { it.order == order } ?: Idle
    }
}

fun Int.toDeliveryState(): DeliveryState = DeliveryState.fromOrder(this)

interface DeliveryManager{
    val deliveryState : StateFlow<DeliveryState>
    fun syncStateFromRemote(remoteStatus: Int): Result<Unit>
    fun updateDeliveryState(newState: Int): Result<Unit>

    fun cancelDelivery()
}


class DeliveryManagerImpl() : DeliveryManager{
    private val _deliveryState = MutableStateFlow<DeliveryState>(value = DeliveryState.Idle)
    override val deliveryState: StateFlow<DeliveryState>
        get() = _deliveryState

    override fun syncStateFromRemote(remoteStatus: Int): Result<Unit> {
        return runCatching {
            val newState = remoteStatus.toDeliveryState()
            if (_deliveryState.value != newState) {
                _deliveryState.value = newState
            }
        }
    }

    override fun updateDeliveryState(newState: Int): Result<Unit> {
        return runCatching {
            val old = _deliveryState.value
            val new = newState.toDeliveryState()
            println("DeliveryState $old → $new")

            if (isValidTransition(old = old, new = new)) {
                _deliveryState.value = new
            } else {
                throw IllegalArgumentException(
                    "Transición inválida: ${old::class.simpleName} → ${new::class.simpleName}"
                )
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

