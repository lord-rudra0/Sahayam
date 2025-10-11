package com.rudra.sahayam.domain.model

data class AlertItem(
    val id: String,
    val title: String,
    val severity: String, // "high"/"medium"/"low"
    val location: String,
    val time: String
)
