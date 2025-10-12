package com.rudra.sahayam.data.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.ParcelUuid
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BleDiscoveryService @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val bluetoothAdapter by lazy {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private val bleScanner by lazy {
        bluetoothAdapter.bluetoothLeScanner
    }

    private val advertiser by lazy {
        bluetoothAdapter.bluetoothLeAdvertiser
    }

    private val SERVICE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // Standard SPP UUID

    private var isAdvertising = false
    private var isScanning = false

    @SuppressLint("MissingPermission")
    fun startDiscovery(connectLink: String): Flow<String> = callbackFlow {
        val scanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                result?.scanRecord?.serviceData?.get(ParcelUuid(SERVICE_UUID))?.let {
                    trySend(String(it))
                }
            }
        }

        val scanFilter = ScanFilter.Builder()
            .setServiceUuid(ParcelUuid(SERVICE_UUID))
            .build()

        val scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()

        if (!isScanning) {
            bleScanner.startScan(listOf(scanFilter), scanSettings, scanCallback)
            isScanning = true
        }
        
        val advertiseCallback = object : AdvertiseCallback() {}

        val advertiseSettings = AdvertiseSettings.Builder()
            .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
            .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
            .setConnectable(false)
            .build()

        val advertiseData = AdvertiseData.Builder()
            .addServiceUuid(ParcelUuid(SERVICE_UUID))
            .addServiceData(ParcelUuid(SERVICE_UUID), connectLink.toByteArray())
            .build()
        
        if(!isAdvertising) {
            advertiser.startAdvertising(advertiseSettings, advertiseData, advertiseCallback)
            isAdvertising = true
        }

        awaitClose { 
            bleScanner.stopScan(scanCallback)
            isScanning = false
            advertiser.stopAdvertising(advertiseCallback)
            isAdvertising = false
        }
    }
}
