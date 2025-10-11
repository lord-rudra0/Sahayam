package com.rudra.sahayam.util

import kotlinx.coroutines.flow.Flow

interface BluetoothObserver {
    fun observe(): Flow<Boolean>
}
