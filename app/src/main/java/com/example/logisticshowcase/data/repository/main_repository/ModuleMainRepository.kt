package com.example.logisticshowcase.data.repository.main_repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleMainRepository {

    @Singleton
    @Provides
    fun providerMainRepository(): MainRepository {
        return MainHardCodeRepository()
    }

}