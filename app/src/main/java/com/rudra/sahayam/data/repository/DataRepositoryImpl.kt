package com.rudra.sahayam.data.repository

import com.rudra.sahayam.data.api.ApiService
import com.rudra.sahayam.data.db.AlertDao
import com.rudra.sahayam.data.db.AlertEntity
import com.rudra.sahayam.domain.model.AlertItem
import com.rudra.sahayam.domain.model.ResourceItem
import com.rudra.sahayam.domain.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val alertDao: AlertDao
) : DataRepository {

    override fun getAlerts(lat: Double?, lon: Double?, radius: Int): Flow<List<AlertItem>> = flow {
        // 1. Emit cached data first
        val cachedAlerts = alertDao.getAlerts().map { entities -> entities.map { it.toDomainModel() } }
        emitAll(cachedAlerts)

        // 2. Fetch new data from network
        try {
            val response = api.getAlerts(lat, lon, radius)
            val alertItems = response.alerts
            // 3. Cache new data
            alertDao.cacheAlerts(alertItems.map { it.toEntity() })
        } catch (e: IOException) {
            // Network error, do nothing. The UI is already displaying cached data.
            println("Network error fetching alerts: ${e.message}")
        }
    }

    override fun getResources(lat: Double?, lon: Double?, radius: Int): Flow<List<ResourceItem>> = flow {
        // This can be upgraded to an offline-first model later, similar to alerts.
        try {
            val response = api.getResources(lat, lon, radius)
            emit(response.resources)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    // --- Mappers ---
    private fun AlertEntity.toDomainModel(): AlertItem {
        return AlertItem(id, type, severity, location, description, timestamp)
    }

    private fun AlertItem.toEntity(): AlertEntity {
        return AlertEntity(id, type, severity, location, description, timestamp)
    }
}
