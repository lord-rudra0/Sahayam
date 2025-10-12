package com.rudra.sahayam.domain.model

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    val status: String,
    @SerializedName("user_id") val userId: String,
    val message: String,
    val token: String,
    @SerializedName("refresh_token") val refreshToken: String
)
