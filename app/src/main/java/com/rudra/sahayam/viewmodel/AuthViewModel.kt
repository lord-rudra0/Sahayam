package com.rudra.sahayam.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.sahayam.data.api.request.LoginRequest
import com.rudra.sahayam.data.api.request.SignUpRequest
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
    val isAuthenticated: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    // -- UI State for input fields --
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var phone by mutableStateOf("")
    var password by mutableStateOf("")
    var location by mutableStateOf("")


    fun login() {
        viewModelScope.launch {
            val loginRequest = LoginRequest(email, password)
            authRepository.login(loginRequest)
                .onStart { _authState.value = AuthState(isLoading = true) }
                .catch { e -> _authState.value = AuthState(error = e.message) }
                .collect { result ->
                    result.fold(
                        onSuccess = { _authState.value = AuthState(isAuthenticated = true) },
                        onFailure = { e -> _authState.value = AuthState(error = e.message) }
                    )
                }
        }
    }

    fun signup() {
        viewModelScope.launch {
            val signUpRequest = SignUpRequest(name, email, phone, password, "citizen", location)
            authRepository.signup(signUpRequest)
                .onStart { _authState.value = AuthState(isLoading = true) }
                .catch { e -> _authState.value = AuthState(error = e.message) }
                .collect { result ->
                    result.fold(
                        onSuccess = { _authState.value = AuthState(isAuthenticated = true) },
                        onFailure = { e -> _authState.value = AuthState(error = e.message) }
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
