package com.rudra.sahayam.data.repository

import com.rudra.sahayam.data.api.ApiService
import com.rudra.sahayam.data.api.request.LoginRequest
import com.rudra.sahayam.data.api.request.SignUpRequest
import com.rudra.sahayam.data.local.SessionManager
import com.rudra.sahayam.domain.model.LoginResponse
import com.rudra.sahayam.domain.model.SignUpResponse
import com.rudra.sahayam.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager
) : AuthRepository {

    override fun login(request: LoginRequest): Flow<Result<LoginResponse>> = flow {
        try {
            val response = apiService.login(request)
            if (response.status == "success") {
                sessionManager.saveToken(response.token)
                sessionManager.saveUser(response.user)
                emit(Result.success(response))
            } else {
                emit(Result.failure(Exception("Login failed: Invalid credentials")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun signup(request: SignUpRequest): Flow<Result<SignUpResponse>> = flow {
        try {
            val response = apiService.signup(request)
            if (response.status == "success") {
                sessionManager.saveToken(response.token)
                emit(Result.success(response))
            } else {
                emit(Result.failure(Exception(response.message)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun startGuestSession() {
        sessionManager.startGuestSession()
    }

    override fun logout() {
        sessionManager.logout()
        // Here you might also want to call apiService.logout() if you need to invalidate the token on the server
    }
}
