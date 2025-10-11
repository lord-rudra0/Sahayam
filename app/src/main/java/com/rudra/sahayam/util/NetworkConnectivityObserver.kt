package com.rudra.sahayam.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkConnectivityObserver @Inject constructor(
    @ApplicationContext private val context: Context
): ConnectivityObserver {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<ConnectivityObserver.NetworkStatus> {
        return callbackFlow {
            val initialStatus = getCurrentNetworkStatus()
            launch { send(initialStatus) }

            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(getCurrentNetworkStatus()) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(ConnectivityObserver.NetworkStatus(ConnectivityObserver.Status.Losing, getConnectionType())) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(ConnectivityObserver.NetworkStatus(ConnectivityObserver.Status.Lost, ConnectivityObserver.ConnectionType.None)) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(ConnectivityObserver.NetworkStatus(ConnectivityObserver.Status.Unavailable, ConnectivityObserver.ConnectionType.None)) }
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }

    private fun getCurrentNetworkStatus(): ConnectivityObserver.NetworkStatus {
        val connected = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
        val connectionType = if (connected) getConnectionType() else ConnectivityObserver.ConnectionType.None
        val status = if (connected) ConnectivityObserver.Status.Available else ConnectivityObserver.Status.Unavailable
        return ConnectivityObserver.NetworkStatus(status, connectionType)
    }

    private fun getConnectionType(): ConnectivityObserver.ConnectionType {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return when {
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> ConnectivityObserver.ConnectionType.Wifi
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> ConnectivityObserver.ConnectionType.Cellular
            else -> ConnectivityObserver.ConnectionType.None
        }
    }
}
