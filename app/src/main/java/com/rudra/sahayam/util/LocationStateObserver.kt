package com.rudra.sahayam.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class LocationStateObserver @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationObserver {
    override fun observe(): Flow<Boolean> {
        return callbackFlow {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    if (intent.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
                        trySend(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                    }
                }
            }

            // Initial state
            trySend(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))

            context.registerReceiver(receiver, IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION))

            awaitClose { context.unregisterReceiver(receiver) }
        }
    }
}
