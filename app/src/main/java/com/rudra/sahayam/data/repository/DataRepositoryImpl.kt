package com.rudra.sahayam.data.repository

import com.rudra.sahayam.data.api.ApiService
import com.rudra.sahayam.domain.model.AlertItem
import com.rudra.sahayam.domain.model.ResourceItem
import com.rudra.sahayam.domain.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val api: ApiService
) : DataRepository {

    override fun getAlerts(lat: Double?, lon: Double?, radius: Int): Flow<List<AlertItem>> = flow {
        try {
            val response = api.getAlerts(lat, lon, radius)
            emit(response.alerts)
        } catch (e: Exception) {
            // In a real app, you'd want to handle this error more gracefully
            // (e.g., by emitting a cached value or a specific error state)
            emit(emptyList())
        }
    }

    override fun getResources(lat: Double?, lon: Double?, radius: Int): Flow<List<ResourceItem>> = flow {
        try {
            val response = api.getResources(lat, lon, radius)
            emit(response.resources)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}
