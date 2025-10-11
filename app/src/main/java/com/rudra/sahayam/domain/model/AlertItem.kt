package com.rudra.sahayam.domain.model

import com.google.gson.annotations.SerializedName

// Wrapper class to match the API response { "alerts": [...] }
data class AlertResponse(
    val alerts: List<AlertItem>
)

data class AlertItem(
    val id: String,
    val type: String,
    val severity: String,
    val location: Location,
    val description: String,
    val timestamp: String
)
