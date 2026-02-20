package com.example.logisticshowcase.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
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

}