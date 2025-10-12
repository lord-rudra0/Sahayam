package com.rudra.sahayam.domain.model

data class LoginResponse(
    val status: String,
    val user: User,
    val token: String
)
