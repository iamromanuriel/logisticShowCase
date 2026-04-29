package com.example.logisticshowcase.ui.screen.login

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logisticshowcase.R
import com.example.logisticshowcase.data.repository.AuthRepository
import com.example.logisticshowcase.data.repository.user_repository.UserRepository
import com.example.logisticshowcase.data.usescase.SyncUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
data class LoginState(
    val userName: String = "",
    val password: String = "",
    val userNameError: Any? = null,
    val passwordError: Any? = null,
    val isLoginEnable: Boolean = false,
    val isSuccess: Boolean = false,
    val loading: Boolean = false
)

sealed class LoginIntent(){
    data class OnChangeUserName(val userName: String): LoginIntent()
    data class OnChangePassword(val password: String): LoginIntent()
    object OnLogin: LoginIntent()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val syncUserDataUseCase: SyncUserDataUseCase
): ViewModel(){

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()


    fun onIntent(intent: LoginIntent){
        when(intent){
            is LoginIntent.OnChangeUserName -> onChangeUserName(intent.userName)
            is LoginIntent.OnChangePassword -> onChangePassword(intent.password)
            is LoginIntent.OnLogin -> onLogin()
        }
    }


    private fun onChangeUserName(name: String){

        _state.update { stateCurrent ->
            stateCurrent.copy(
                userName = name
            )
        }
    }

    private fun onChangePassword(password: String){
        _state.update { stateCurrent ->
            stateCurrent.copy(
                password = password
            )
        }
    }


    private fun onLogin(){
        viewModelScope.launch {
            _state.update { stateCurrent ->
                stateCurrent.copy(
                    loading = true
                )
            }

            val userName = _state.value.userName

            if(isValidateUserName(userName = userName)){

                userRepository.onSignIn(
                    user = userName,
                    password = _state.value.password
                ).onSuccess {

                    _state.update { stateCurrent ->
                        stateCurrent.copy(
                            userNameError = null,
                            loading = false,
                            isSuccess = true
                        )
                    }
                    syncUserDataUseCase.invoke()
                }.onFailure {
                    println("onLogin failure :: ${it.message}")
                    _state.update { stateCurrent ->
                        stateCurrent.copy(
                            userNameError = R.string.invaliate_username,
                            loading = false
                        )
                    }
                }

            }else{
                _state.update { stateCurrent ->
                    stateCurrent.copy(
                        userNameError = R.string.invaliate_username,
                        loading = false
                    )
                }
            }
        }
    }

    fun isValidateUserName(userName: String): Boolean{
        val USERNAME_REGEX = "^[a-zA-Z0-9][a-zA-Z0-9._]{1,28}[a-zA-Z0-9]$".toRegex()
        return userName.matches(USERNAME_REGEX)
    }

}