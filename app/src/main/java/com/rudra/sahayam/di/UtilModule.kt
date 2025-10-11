package com.rudra.sahayam.di

import com.rudra.sahayam.util.BluetoothObserver
import com.rudra.sahayam.util.BluetoothStateObserver
import com.rudra.sahayam.util.ConnectivityObserver
import com.rudra.sahayam.util.LocationObserver
import com.rudra.sahayam.util.LocationStateObserver
import com.rudra.sahayam.util.NetworkConnectivityObserver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityObserver(networkConnectivityObserver: NetworkConnectivityObserver): ConnectivityObserver

    @Binds
    @Singleton
    abstract fun bindBluetoothObserver(bluetoothStateObserver: BluetoothStateObserver): BluetoothObserver

    @Binds
    @Singleton
    abstract fun bindLocationObserver(locationStateObserver: LocationStateObserver): LocationObserver
}
