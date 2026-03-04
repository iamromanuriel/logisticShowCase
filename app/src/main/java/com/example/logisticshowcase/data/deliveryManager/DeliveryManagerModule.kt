package com.example.logisticshowcase.data.deliveryManager

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeliveryManagerModule {

    @Singleton
    @Provides
    fun provideDeliveryManager(): DeliveryManager {
        return DeliveryManagerImpl()
    }

}