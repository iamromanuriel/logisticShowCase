package com.example.logisticshowcase.data.repository.user_repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserRepositoryImp : UserRepository {
    private val _userLogIn = MutableStateFlow(false)
    override val userLogIn: StateFlow<Boolean>
        get() = _userLogIn

    init {
        _userLogIn.value = true
    }

}