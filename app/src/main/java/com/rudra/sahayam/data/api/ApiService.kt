package com.rudra.sahayam.data.api

import com.rudra.sahayam.domain.model.AlertResponse
import com.rudra.sahayam.domain.model.ResourceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/alerts")
    suspend fun getAlerts(
        @Query("lat") lat: Double? = null,
        @Query("lon") lon: Double? = null,
        @Query("radius") radius: Int = 50
    ): AlertResponse

    @GET("api/resources")
    suspend fun getResources(
        @Query("lat") lat: Double? = null,
        @Query("lon") lon: Double? = null,
        @Query("radius") radius: Int = 50
    ): ResourceResponse
}
