package com.example.logisticshowcase.data.Api

import retrofit2.http.GET

interface ApiService {
    @GET("get-order-assigned")
    suspend fun getClients()


}