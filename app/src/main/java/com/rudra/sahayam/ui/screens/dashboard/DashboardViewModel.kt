package com.rudra.sahayam.ui.screens.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.sahayam.domain.location.LocationTracker
import com.rudra.sahayam.domain.model.AlertItem
import com.rudra.sahayam.domain.model.ResourceItem
import com.rudra.sahayam.domain.repository.DataRepository
import com.rudra.sahayam.util.BluetoothObserver
import com.rudra.sahayam.util.ConnectivityObserver
import com.rudra.sahayam.util.LocationObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repo: DataRepository,
    private val locationTracker: LocationTracker,
    private val connectivityObserver: ConnectivityObserver,
    private val bluetoothObserver: BluetoothObserver,
    private val locationObserver: LocationObserver
) : ViewModel() {

    var alertsState by mutableStateOf<List<AlertItem>>(emptyList())
        private set

    var resourcesState by mutableStateOf<List<ResourceItem>>(emptyList())
        private set

    var userAddress by mutableStateOf("Locating...")
        private set

    var networkStatus by mutableStateOf(
        ConnectivityObserver.NetworkStatus(
            ConnectivityObserver.Status.Unavailable,
            ConnectivityObserver.ConnectionType.None
        )
    )
        private set

    var isBluetoothEnabled by mutableStateOf(false)
        private set

    private var isLocationEnabled by mutableStateOf(false) // Private, used for triggering refresh

    init {
        connectivityObserver.observe().onEach {
            networkStatus = it
        }.launchIn(viewModelScope)

        bluetoothObserver.observe().onEach {
            isBluetoothEnabled = it
        }.launchIn(viewModelScope)

        locationObserver.observe().distinctUntilChanged().onEach {
            val wasLocationDisabled = !isLocationEnabled
            isLocationEnabled = it
            if (wasLocationDisabled && it) {
                loadData()
            }
        }.launchIn(viewModelScope)
    }

    fun loadData() {
        viewModelScope.launch {
            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                userAddress = locationTracker.getAddress(location) ?: "Unknown Location"
                repo.getAlerts(location.latitude, location.longitude).onEach {
                    alertsState = it
                }.launchIn(viewModelScope)
                repo.getResources(location.latitude, location.longitude).onEach {
                    resourcesState = it
                }.launchIn(viewModelScope)
            } else {
                userAddress = if(isLocationEnabled) "Could not determine location" else "Location is disabled"
                repo.getAlerts(null, null).onEach {
                    alertsState = it
                }.launchIn(viewModelScope)
                repo.getResources(null, null).onEach {
                    resourcesState = it
                }.launchIn(viewModelScope)
            }
        }
    }
}
