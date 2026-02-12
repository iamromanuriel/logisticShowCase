package com.example.logisticshowcase.data.repository.user_repository

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
    fun providerUserRepository(): UserRepository {
        return UserRepositoryImp()
    }
}