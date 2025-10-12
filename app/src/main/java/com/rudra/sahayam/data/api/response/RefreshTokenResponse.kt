package com.rudra.sahayam.data.api.response

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(
    val status: String,
    @SerializedName("new_token") val newToken: String
)
