package com.rudra.sahayam.data.api

import com.rudra.sahayam.data.api.request.LoginRequest
import com.rudra.sahayam.data.api.request.RefreshTokenRequest
import com.rudra.sahayam.data.api.request.SignUpRequest
import com.rudra.sahayam.data.api.request.SyncGuestRequest
import com.rudra.sahayam.data.api.response.GenericStatusResponse
import com.rudra.sahayam.data.api.response.RefreshTokenResponse
import com.rudra.sahayam.data.api.response.SyncGuestResponse
import com.rudra.sahayam.domain.model.AlertResponse
import com.rudra.sahayam.domain.model.LoginResponse
import com.rudra.sahayam.domain.model.ResourceResponse
import com.rudra.sahayam.domain.model.SignUpResponse
import com.rudra.sahayam.domain.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    // region Dashboard Endpoints
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
    // endregion

    // region Auth Endpoints
    @POST("api/auth/signup")
    suspend fun signup(@Body request: SignUpRequest): SignUpResponse

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("api/auth/logout")
    suspend fun logout(@Body request: Map<String, String>): GenericStatusResponse

    @POST("api/auth/refresh-token")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): RefreshTokenResponse
    // endregion

    // region User Endpoints
    @GET("api/users/profile")
    suspend fun getProfile(): User

    @POST("api/users/update")
    suspend fun updateProfile(@Body request: Map<String, String>): GenericStatusResponse

    @POST("api/users/sync-guest")
    suspend fun syncGuestData(@Body request: SyncGuestRequest): SyncGuestResponse
    // endregion
}
