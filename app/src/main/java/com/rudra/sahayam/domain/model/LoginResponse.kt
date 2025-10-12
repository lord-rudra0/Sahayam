package com.rudra.sahayam.domain.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val success: Boolean,
    @SerializedName("access_token") val accessToken: String,
    val data: User
)
