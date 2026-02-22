package com.example.logisticshowcase.util.location

import android.content.Context
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
class LocationUtil @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    @param:ApplicationContext private val context: Context
) {

    suspend fun getCurrentLocation() = withContext(Dispatchers.IO){
        val callbackTokenSource = CancellationTokenSource()
        runCatching {
            val request = CurrentLocationRequest.Builder()
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                .setMaxUpdateAgeMillis(30000L)
                .build()

            withTimeout(35.seconds){
                fusedLocationProviderClient.getCurrentLocation(
                    request, callbackTokenSource.token
                ).await()
            }
        }.onFailure {
            if(it is CancellationException){
                callbackTokenSource.cancel()
            }
        }
    }

}