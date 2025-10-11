package com.rudra.sahayam.data.repository

import com.rudra.sahayam.domain.model.AlertItem
import com.rudra.sahayam.domain.model.ResourceItem
import com.rudra.sahayam.domain.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDataRepository : DataRepository {
    override fun getAlerts(): Flow<List<AlertItem>> = flow {
        val sample = listOf(
            AlertItem("a1", "Flood Warning", "high", "Mumbai Suburban", "10m ago"),
            AlertItem("a2", "Landslide Risk", "medium", "Nilgiris", "30m ago"),
            AlertItem("a3", "Heat Advisory", "low", "Hyderabad", "1h ago")
        )
        emit(sample)
    }

    override fun getResources(): Flow<List<ResourceItem>> = flow {
        val sample = listOf(
            ResourceItem("r1", "Bandra Shelter", "Shelter", 1.2, true),
            ResourceItem("r2", "Water Station - A", "Water", 0.7, true),
            ResourceItem("r3", "Medical Camp B", "Medical", 3.5, false)
        )
        emit(sample)
    }
}
