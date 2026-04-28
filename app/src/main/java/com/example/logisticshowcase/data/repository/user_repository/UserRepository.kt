package com.example.logisticshowcase.data.repository.user_repository

import com.example.logisticshowcase.data.db.entity.UserEntity
import com.example.logisticshowcase.data.db.entity.Vehicle
import com.example.logisticshowcase.data.firebase.collection.UserCollection
import com.example.logisticshowcase.data.firebase.collection.VehicleCollection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {
    val userLogIn: StateFlow<Boolean>
    suspend fun onSignIn(user: String, password: String): Result<Unit>
    suspend fun getUserFirestore(): Result<UserCollection>
    suspend fun getVehicleFirestore(): Result<VehicleCollection>
    suspend fun localSaveUser(user: UserEntity)
    suspend fun localSaveVehicle(vehicle: Vehicle)
    fun userLocalFlow(): Flow<UserEntity>
    fun vehicleLocalFlow(): Flow<Vehicle>
}