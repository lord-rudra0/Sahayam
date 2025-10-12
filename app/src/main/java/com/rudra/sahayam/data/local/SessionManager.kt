package com.rudra.sahayam.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.rudra.sahayam.domain.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(@ApplicationContext context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("Saha_Prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val KEY_TOKEN = "token"
        private const val KEY_USER_PROFILE = "user_profile"
        private const val KEY_IS_GUEST = "is_guest"
        private const val KEY_GUEST_ID = "guest_id"
    }

    // --- Authenticated User Session ---
    fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }

    fun saveUser(user: User) {
        val userJson = gson.toJson(user)
        prefs.edit().putString(KEY_USER_PROFILE, userJson).apply()
    }

    fun getUser(): User? {
        val userJson = prefs.getString(KEY_USER_PROFILE, null)
        return gson.fromJson(userJson, User::class.java)
    }

    // --- Guest Session ---
    fun startGuestSession() {
        val guestId = "guest_${System.currentTimeMillis()}"
        prefs.edit()
            .putBoolean(KEY_IS_GUEST, true)
            .putString(KEY_GUEST_ID, guestId)
            .apply()
    }

    fun isGuest(): Boolean {
        return prefs.getBoolean(KEY_IS_GUEST, false)
    }

    fun getGuestId(): String? {
        return prefs.getString(KEY_GUEST_ID, null)
    }

    // --- General Session Management ---
    fun logout() {
        prefs.edit().clear().apply()
    }
}
