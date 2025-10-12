package com.rudra.sahayam.data.repository

import com.rudra.sahayam.data.api.ApiService
import com.rudra.sahayam.data.api.request.LocalReport
import com.rudra.sahayam.data.api.request.LoginRequest
import com.rudra.sahayam.data.api.request.RefreshTokenRequest
import com.rudra.sahayam.data.api.request.SignUpRequest
import com.rudra.sahayam.data.api.request.SyncGuestRequest
import com.rudra.sahayam.data.api.response.SyncGuestResponse
import com.rudra.sahayam.data.db.ReportDao
import com.rudra.sahayam.data.local.SessionManager
import com.rudra.sahayam.domain.model.LoginResponse
import com.rudra.sahayam.domain.model.SignUpResponse
import com.rudra.sahayam.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val sessionManager: SessionManager,
    private val reportDao: ReportDao
) : AuthRepository {

    override fun login(request: LoginRequest): Flow<Result<LoginResponse>> = flow {
        try {
            val response = apiService.login(request)
            if (response.success) {
                sessionManager.saveToken(response.accessToken)
                sessionManager.saveUser(response.data)
                // Note: The new API does not provide a refresh token on login.
                // The auto-refresh feature will need to be re-evaluated.
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
            if (response.success) {
                emit(Result.success(response))
            } else {
                // Assuming the exception will carry the message for validation errors etc.
                emit(Result.failure(Exception(response.message)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun refreshToken(refreshToken: String): Flow<Result<String>> = flow {
        try {
            val response = apiService.refreshToken(RefreshTokenRequest(refreshToken))
            if (response.status == "success") {
                sessionManager.saveToken(response.newToken)
                emit(Result.success(response.newToken))
            } else {
                emit(Result.failure(Exception("Failed to refresh token")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun syncGuestData(): Flow<Result<SyncGuestResponse>> = flow {
        try {
            val guestId = sessionManager.getGuestId()
            val userId = sessionManager.getUser()?.userId
            if (guestId == null || userId == null) {
                emit(Result.failure(Exception("Not a guest or user not found")))
                return@flow
            }

            val localReports = reportDao.getAllReports().map { LocalReport(it.id, it.content) }
            if (localReports.isEmpty()) {
                // No need to sync if there's no data
                emit(Result.success(SyncGuestResponse("no-data", 0)))
                return@flow
            }

            val request = SyncGuestRequest(guestId, userId, localReports, emptyList())
            val response = apiService.syncGuestData(request)

            if (response.status == "merged") {
                reportDao.deleteAllReports() // Clear local data after successful sync
                emit(Result.success(response))
            } else {
                emit(Result.failure(Exception("Data sync failed")))
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
    }
}
