package com.example.logisticshowcase.data.repository.user_repository

import android.util.Log
import com.example.logisticshowcase.data.db.AppDatabase
import com.example.logisticshowcase.data.db.entity.UserEntity
import com.example.logisticshowcase.data.db.entity.Vehicle
import com.example.logisticshowcase.data.firebase.FirestoreDataSource
import com.example.logisticshowcase.data.firebase.collection.UserCollection
import com.example.logisticshowcase.data.firebase.collection.VehicleCollection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserRepositoryImp (
    private val database: AppDatabase,
    private val firestore: FirestoreDataSource
) : UserRepository {
    private val _userLogIn = MutableStateFlow(false)
    override val userLogIn: StateFlow<Boolean>
        get() = _userLogIn

    override suspend fun getUserFirestore(): Result<UserCollection> {
        return firestore.getUser()
    }

    override suspend fun getVehicleFirestore(): Result<VehicleCollection> {
        return firestore.getVehicle()
    }

    override suspend fun localSaveUser(user: UserEntity) {
        database.userDao().insert(data = user)
    }

    override suspend fun localSaveVehicle(vehicle: Vehicle) {
        database.vehicleDao().saveVehicle(vehicle)
    }

    override fun userLocalFlow(): Flow<UserEntity> {
        return database.userDao().getUser(1)
    }

    override fun vehicleLocalFlow(): Flow<Vehicle> {
        return database.vehicleDao().getVehicle()
    }

    init {
        _userLogIn.value = true
    }



}