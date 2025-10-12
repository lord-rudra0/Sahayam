package com.rudra.sahayam.domain.repository

import com.rudra.sahayam.data.api.request.LoginRequest
import com.rudra.sahayam.data.api.request.SignUpRequest
import com.rudra.sahayam.domain.model.LoginResponse
import com.rudra.sahayam.domain.model.SignUpResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(request: LoginRequest): Flow<Result<LoginResponse>>
    fun signup(request: SignUpRequest): Flow<Result<SignUpResponse>>
    fun startGuestSession()
    fun logout()
    fun refreshToken(refreshToken: String): Flow<Result<String>>
}
