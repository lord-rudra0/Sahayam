package com.rudra.sahayam.domain.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id") val userId: String,
    val name: String,
    val email: String,
    val role: String,
    val location: String,
    val phone: String? = null // Phone might not always be present
)
