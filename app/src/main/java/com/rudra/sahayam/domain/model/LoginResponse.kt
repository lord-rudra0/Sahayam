package com.rudra.sahayam.domain.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val status: String,
    val user: User,
    val token: String,
    @SerializedName("refresh_token") val refreshToken: String
)
