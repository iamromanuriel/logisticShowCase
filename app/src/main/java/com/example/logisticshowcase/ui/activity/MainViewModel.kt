package com.example.logisticshowcase.ui.activity

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logisticshowcase.data.repository.main_repository.MainRepository
import com.example.logisticshowcase.data.repository.user_repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@Immutable
data class MainState(
    val userLogIn: Boolean = false
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
): ViewModel() {

    private val _state = MutableStateFlow(value = MainState())
    val state = _state.combine(
        userRepository.userLogIn
    ){ stateCurrent, userLogIn ->
        stateCurrent.copy(
            userLogIn = userLogIn
        )
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MainState()
    )

}