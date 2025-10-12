package com.rudra.sahayam.ui.screens.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.rudra.sahayam.di.MeshrabiyaManager
import com.rudra.sahayam.util.ConnectivityObserver
import com.ustadmobile.meshrabiya.vnet.LocalNodeState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val virtualNode = MeshrabiyaManager.virtualNode

    val meshrabiyaState = virtualNode.state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LocalNodeState()
        )

    private val _userAddress = mutableStateOf("Getting your location...")
    val userAddress: State<String> = _userAddress

    private val _isGuest = mutableStateOf(false)
    val isGuest: State<Boolean> = _isGuest

    val alerts = emptyList<com.rudra.sahayam.domain.model.AlertItem>()

    val resources = emptyList<com.rudra.sahayam.domain.model.ResourceItem>()

    val networkStatus = connectivityObserver.observe()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 
            ConnectivityObserver.NetworkStatus(ConnectivityObserver.Status.Unavailable, ConnectivityObserver.ConnectionType.None))

    val isBluetoothEnabled = mutableStateOf(false)

    init {
//        getCurrentLocation()
//        bleClient.startServer()
    }

    private fun getCurrentLocation() {
        viewModelScope.launch {
//            locationTracker.getCurrentLocation()?.let { location ->
//                _userAddress.value = "${location.latitude}, ${location.longitude}"
//            }
        }
    }

    fun onShareLocation() {

    }

    @SuppressLint("MissingPermission")
    fun onCreateHotspot() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.startLocalOnlyHotspot(object : WifiManager.LocalOnlyHotspotCallback() {
            override fun onStarted(reservation: WifiManager.LocalOnlyHotspotReservation) {
                super.onStarted(reservation)
                val ssid = reservation.wifiConfiguration?.SSID
                val password = reservation.wifiConfiguration?.preSharedKey
                //TODO: Share these details with other devices
            }

            override fun onStopped() {
                super.onStopped()
            }

            override fun onFailed(reason: Int) {
                super.onFailed(reason)
            }
        }, null)
    }

    fun onConnectToHotspot() {

    }

    fun onStartBleDiscovery() {
        viewModelScope.launch {
//            val discoveredDevices = bleClient.discoverDevices()
            //TODO: Do something with the list of devices
        }
    }

    override fun onCleared() {
        super.onCleared()
//        bleClient.stopServer()
    }
}
