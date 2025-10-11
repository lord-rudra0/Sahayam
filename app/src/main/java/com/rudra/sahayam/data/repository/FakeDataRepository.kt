package com.rudra.sahayam.data.repository

import com.rudra.sahayam.domain.model.AlertItem
import com.rudra.sahayam.domain.model.Location
import com.rudra.sahayam.domain.model.ResourceItem
import com.rudra.sahayam.domain.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeDataRepository @Inject constructor(): DataRepository {
    override fun getAlerts(lat: Double?, lon: Double?, radius: Int): Flow<List<AlertItem>> = flow {
        val sample = listOf(
            AlertItem("a1", "flood", "high", Location(19.0760, 72.8777), "Flood Warning in Mumbai Suburban", "2024-05-23T10:00:00Z"),
            AlertItem("a2", "landslide", "medium", Location(11.4102, 76.6950), "Landslide Risk in Nilgiris", "2024-05-23T09:30:00Z"),
            AlertItem("a3", "heatwave", "low", Location(17.3850, 78.4867), "Heat Advisory in Hyderabad", "2024-05-23T09:00:00Z")
        )
        emit(sample)
    }

    override fun getResources(lat: Double?, lon: Double?, radius: Int): Flow<List<ResourceItem>> = flow {
        val sample = listOf(
            ResourceItem("r1", "Shelter", "Bandra Shelter", 150, true, Location(19.0596, 72.8406)),
            ResourceItem("r2", "Water", "Water Station - A", 500, true, Location(19.0590, 72.8400)),
            ResourceItem("r3", "Medical", "Medical Camp B", 0, false, Location(19.0600, 72.8390))
        )
        emit(sample)
    }
}
