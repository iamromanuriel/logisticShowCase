package com.example.logisticshowcase.data.usescase

import com.example.logisticshowcase.data.db.entity.UserEntity
import com.example.logisticshowcase.data.db.entity.Vehicle
import com.example.logisticshowcase.data.repository.user_repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

open class SyncUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun invoke(): Result<Unit> {
         return coroutineScope {
            runCatching {

                val userDeferred = async { userRepository.getUserFirestore() }
                val vehicleDeferred = async { userRepository.getVehicleFirestore() }

                val userResult = userDeferred.await()
                val vehicleResult = vehicleDeferred.await()

                val userCollection = userResult.getOrThrow()
                val vehicleCollection = vehicleResult.getOrThrow()


                val user = UserEntity(
                    name = userCollection.user,
                    lastName = userCollection.lastName,
                )
                userRepository.localSaveUser(user)

                val vehicle = Vehicle(
                    brand = vehicleCollection.brand,
                    model = vehicleCollection.model,
                    year = vehicleCollection.year,
                    plate = vehicleCollection.plate,
                    type = vehicleCollection.type,
                    description = vehicleCollection.description
                )

                userRepository.localSaveVehicle(vehicle = vehicle)

                Unit
            }
        }
    }
}