package com.rudra.sahayam.util

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class BluetoothStateObserver @Inject constructor(
    @ApplicationContext private val context: Context
) : BluetoothObserver {
    override fun observe(): Flow<Boolean> {
        return callbackFlow {
            val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            if (bluetoothAdapter == null) {
                trySend(false)
                return@callbackFlow
            }

            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    if (intent.action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                        val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                        trySend(state == BluetoothAdapter.STATE_ON)
                    }
                }
            }

            // Initial state
            trySend(bluetoothAdapter.isEnabled)

            context.registerReceiver(receiver, IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED))

            awaitClose { context.unregisterReceiver(receiver) }
        }
    }
}
