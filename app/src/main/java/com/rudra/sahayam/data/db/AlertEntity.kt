package com.rudra.sahayam.data.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rudra.sahayam.domain.model.Location

@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey val id: String,
    val type: String,
    val severity: String,
    @Embedded(prefix = "loc_") val location: Location,
    val description: String,
    val timestamp: String
)
