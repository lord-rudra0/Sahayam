package com.rudra.sahayam.ui.screens.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.sahayam.domain.model.AlertItem
import com.rudra.sahayam.domain.model.ResourceItem
import com.rudra.sahayam.domain.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: DataRepository
) : ViewModel() {

    var alertsState by mutableStateOf<List<AlertItem>>(emptyList())
        private set

    var resourcesState by mutableStateOf<List<ResourceItem>>(emptyList())
        private set

    init {
        repo.getAlerts().onEach {
            alertsState = it
        }.launchIn(viewModelScope)

        repo.getResources().onEach {
            resourcesState = it
        }.launchIn(viewModelScope)
    }
}
