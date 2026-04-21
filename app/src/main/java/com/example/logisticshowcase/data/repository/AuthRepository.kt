package com.example.logisticshowcase.data.repository

import com.example.logisticshowcase.data.firebase.FirestoreDataSource
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firestore: FirestoreDataSource
){
    suspend fun testCollectionUser(){
        firestore.testFireStore()
    }
}