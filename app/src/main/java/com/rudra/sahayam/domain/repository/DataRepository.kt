package com.rudra.sahayam.domain.repository

import com.rudra.sahayam.domain.model.AlertItem
import com.rudra.sahayam.domain.model.ResourceItem
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getAlerts(lat: Double?, lon: Double?): Flow<List<AlertItem>>
    fun getResources(lat: Double?, lon: Double?): Flow<List<ResourceItem>>
}
