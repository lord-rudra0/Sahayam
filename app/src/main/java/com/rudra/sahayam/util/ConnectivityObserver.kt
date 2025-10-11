package com.rudra.sahayam.util

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<NetworkStatus>

    data class NetworkStatus(
        val status: Status,
        val type: ConnectionType
    )

    enum class Status {
        Available, Unavailable, Losing, Lost
    }

    enum class ConnectionType {
        Wifi, Cellular, None
    }
}
