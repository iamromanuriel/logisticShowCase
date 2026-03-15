package com.example.logisticshowcase.data.repository.user_repository

import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    val userLogIn: StateFlow<Boolean>

}