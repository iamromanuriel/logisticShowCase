package com.example.logisticshowcase.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logisticshowcase.data.db.entity.UserEntity
import com.example.logisticshowcase.data.db.entity.Vehicle
import com.example.logisticshowcase.data.repository.order_repository.OrderRepository
import com.example.logisticshowcase.data.repository.user_repository.UserRepository
import com.example.logisticshowcase.data.usescase.SyncOrderDataUseCase
import com.example.logisticshowcase.data.usescase.SyncUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val user: UserEntity? = null,
    val vehicle: Vehicle? = null,
    val isLoading: Boolean = false,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val syncUseCase: SyncUserDataUseCase,
    private val orderRepository: OrderRepository,
    private val syncOrderDataUseCase: SyncOrderDataUseCase
): ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = combine(
        _state,
        userRepository.userLocalFlow(),
        userRepository.vehicleLocalFlow()
    ){ state, user, vehicle ->
        state.copy(
            user = user,
            vehicle = vehicle
        )
    }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            //syncUseCase.invoke()
            //syncOrderDataUseCase.invoke()
        }
    }

}