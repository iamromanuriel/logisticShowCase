package com.example.logisticshowcase.data.repository.user_repository

import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    val userLogIn: StateFlow<Boolean>

}