package com.example.logisticshowcase.data.firebase

import com.example.logisticshowcase.data.db.entity.Vehicle
import com.example.logisticshowcase.data.firebase.collection.UserCollection
import com.example.logisticshowcase.data.firebase.collection.VehicleCollection
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun onSave(){
        val data = mapOf("name" to "John Doe", "age" to 30, "city" to "New York", "email" to "john.mclean@examplepetstore.com")
        firestore.collection(
            FirestoreCollection.USERS.value
        ).add(data)
    }

    /*
fun getUserPresenter(): Flow<List<UserCollection>>{
        return firestore
            .collection(CollectionFirebase.USERS.name)
            .snapshots()
            .map { querySnapshot ->
                querySnapshot.documents.map { doc ->
                    doc.data<UserCollection>()
                }
            }
    }     */


    suspend fun testFireStore(){
        firestore
            .collection(FirestoreCollection.USERS.value)
            .get()
            .addOnSuccessListener { result ->
                println("dataTestUser: ${result.documents.size}")
            }

    }


    suspend fun getUser(): Result<UserCollection>{
        return try {
            val docRef = firestore
                .collection(FirestoreCollection.USERS.value)
                .document("HQxZ3EnP0VKsIglkF3Bp")
                .get()
                .await()

            if(docRef.exists()){
                val data = docRef.toObject(UserCollection::class.java)
                if(data != null){
                    Result.success(data)
                }else {
                    Result.failure(Exception("data is null"))
                }
            }else{
                Result.failure(Exception("data is null"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun getVehicle(): Result<VehicleCollection>{
        return try {
            val docRef = firestore
                .collection(FirestoreCollection.VEHICLES.value)
                .document("g5jW0U56UAlvZRyWyINY")
                .get()
                .await()

            if(docRef.exists()){
                val data = docRef.toObject(VehicleCollection::class.java)
                if(data != null){
                    Result.success(data)
                }else {
                    Result.failure(Exception("data is null"))
                }
            }else{
                Result.failure(Exception("data is null"))
            }
        }catch (e: Exception){
            Result.failure(e)

        }
    }

}