package com.rudra.sahayam.data.api

import com.rudra.sahayam.data.local.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Don't add a token to login or signup requests
        if (originalRequest.url.encodedPath.contains("/login") || originalRequest.url.encodedPath.contains("/signup")) {
            return chain.proceed(originalRequest)
        }

        val token = sessionManager.getToken()
        if (token == null || sessionManager.isGuest()) {
            return chain.proceed(originalRequest) // Proceed without token for guest or if not logged in
        }

        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        return chain.proceed(newRequest)
    }
}
