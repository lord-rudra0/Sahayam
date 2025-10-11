package com.rudra.sahayam.data.api


import retrofit2.http.GET

interface ApiService {
    @GET("ping")
    suspend fun ping(): String
}
