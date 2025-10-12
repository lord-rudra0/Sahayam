package com.rudra.sahayam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.sahayam.data.local.SessionManager
import com.rudra.sahayam.ui.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination: StateFlow<String?> = _startDestination

    init {
        viewModelScope.launch {
            if (sessionManager.getToken() != null) {
                // User is already logged in
                _startDestination.value = Routes.HOME
            } else {
                // User is not logged in
                _startDestination.value = Routes.WELCOME
            }
        }
    }
}
