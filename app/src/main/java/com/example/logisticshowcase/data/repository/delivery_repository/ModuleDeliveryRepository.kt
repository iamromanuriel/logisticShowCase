package com.example.logisticshowcase.data.repository.delivery_repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ModuleDeliveryRepository {

    @ViewModelScoped
    @Provides
    fun providerDeliveryRepository(): DeliveryRepository {
        return DeliveryRepositoryImp()
    }

}