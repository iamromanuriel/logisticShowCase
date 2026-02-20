package com.example.logisticshowcase.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val userName: String = "",
    val password: String = "",
    val userNameError: String? = null,
    val passwordError: String? = null,
    val isLoginEnable: Boolean = false,
    val isSuccess: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel(){

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()


    fun onChangeUserName(name: String){
        _state.update { stateCurrent ->
            stateCurrent.copy(
                userName = name
            )
        }
    }

    fun onChangePassword(password: String){
        _state.update { stateCurrent ->
            stateCurrent.copy(
                password = password
            )
        }
    }


    fun onLogin(){
        viewModelScope.launch {}
    }

}