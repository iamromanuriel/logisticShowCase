package com.example.logisticshowcase.data.repository.user_repository

import com.example.logisticshowcase.data.db.AppDatabase
import com.example.logisticshowcase.data.firebase.FirestoreDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ModuleUserRepository {

    @ViewModelScoped
    @Provides
    fun providerUserRepository(appDatabase: AppDatabase, firestoreDataSource: FirestoreDataSource): UserRepository {
        return UserRepositoryImp(database = appDatabase, firestore = firestoreDataSource)
    }
}