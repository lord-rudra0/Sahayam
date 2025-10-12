package com.rudra.sahayam.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.sahayam.data.api.request.LoginRequest
import com.rudra.sahayam.data.api.request.SignUpRequest
import com.rudra.sahayam.data.local.SessionManager
import com.rudra.sahayam.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


data class AuthState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAuthenticated: Boolean = false,
    val isRegistrationSuccessful: Boolean = false,
    val syncMessage: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    // -- UI State for input fields --
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var phone by mutableStateOf("")
    var password by mutableStateOf("")
    var location by mutableStateOf("")

    // -- Role selection for signup --
    val roles = listOf("user", "volunteer", "ngo")
    var selectedRole by mutableStateOf(roles.first())


    fun login() {
        viewModelScope.launch {
            val wasGuest = sessionManager.isGuest()
            val loginRequest = LoginRequest(email, password)
            authRepository.login(loginRequest)
                .onStart { _authState.value = AuthState(isLoading = true) }
                .catch { e -> _authState.value = AuthState(error = e.message) }
                .collect { result ->
                    result.fold(
                        onSuccess = {
                            _authState.value = AuthState(isAuthenticated = true)
                            if (wasGuest) {
                                _syncGuestData()
                            }
                        },
                        onFailure = { e -> _authState.value = AuthState(error = e.message) }
                    )
                }
        }
    }

    fun signup() {
        viewModelScope.launch {
            val signUpRequest = SignUpRequest(name, email, phone, password, selectedRole)
            authRepository.signup(signUpRequest)
                .onStart { _authState.value = AuthState(isLoading = true) }
                .catch { e -> _authState.value = AuthState(error = e.message) }
                .collect { result ->
                    result.fold(
                        onSuccess = {
                            _authState.value = AuthState(isRegistrationSuccessful = true)
                        },
                        onFailure = { e -> _authState.value = AuthState(error = e.message) }
                    )
                }
        }
    }

    private fun _syncGuestData() {
        viewModelScope.launch {
            authRepository.syncGuestData()
                .onStart { _authState.value = AuthState(isAuthenticated = true, syncMessage = "Syncing offline data...") }
                .catch { e -> _authState.value = AuthState(isAuthenticated = true, error = "Sync failed: ${e.message}") }
                .collect { result ->
                    result.fold(
                        onSuccess = { response ->
                            val message = if (response.migratedRecords > 0) "Sync complete: ${response.migratedRecords} records synced." else "Sync complete."
                            _authState.value = AuthState(isAuthenticated = true, syncMessage = message)
                        },
                        onFailure = { e -> _authState.value = AuthState(isAuthenticated = true, error = "Sync failed: ${e.message}") }
                    )
                }
        }
    }

    fun startGuestSession() {
        viewModelScope.launch {
            authRepository.startGuestSession()
        }
    }
}
