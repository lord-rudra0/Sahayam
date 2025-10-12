package com.rudra.sahayam.data.api.request


data class LoginRequest(
    val email: String,
    val password: String
)

data class SignUpRequest(
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val role: String,
    val location: String
)
