package com.example.logisticshowcase.data.repository.order_repository

import com.example.logisticshowcase.data.db.AppDatabase
import com.example.logisticshowcase.data.firebase.FirestoreDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleOrderRepository {

    @Singleton
    @Provides
    fun providerOrderRepository(
        firestore: FirestoreDataSource,
        appDatabase: AppDatabase
    ): OrderRepository {
        return OrderRepositoryImp(firestore = firestore, appDatabase = appDatabase)
    }
}