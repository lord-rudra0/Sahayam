package com.rudra.sahayam.data.api

import com.rudra.sahayam.data.local.SessionManager
import com.rudra.sahayam.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider

class TokenAuthenticator @Inject constructor(
    private val sessionManager: SessionManager,
    // Use Provider to lazily inject AuthRepository and avoid circular dependency
    private val authRepository: Provider<AuthRepository>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // We don't want to retry refreshing the token if the refresh call itself failed
        if (response.request.url.encodedPath.contains("/refresh-token")) {
            return null
        }

        val oldToken = sessionManager.getToken()
        // If the token is the same, another request must have already refreshed it
        if (oldToken != null && response.request.header("Authorization") != "Bearer $oldToken") {
            return response.request.newBuilder()
                .header("Authorization", "Bearer $oldToken")
                .build()
        }

        return runBlocking {
            val refreshToken = sessionManager.getRefreshToken() ?: return@runBlocking null

            val result = authRepository.get().refreshToken(refreshToken).first()

            if (result.isFailure) {
                authRepository.get().logout()
                // Here you might want to navigate to the login screen
                return@runBlocking null
            }

            val newAccessToken = result.getOrNull()!!
            response.request.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()
        }
    }
}
