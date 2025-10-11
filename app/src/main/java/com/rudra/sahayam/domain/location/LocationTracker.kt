package com.rudra.sahayam.domain.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
    suspend fun getAddress(location: Location): String?
}
