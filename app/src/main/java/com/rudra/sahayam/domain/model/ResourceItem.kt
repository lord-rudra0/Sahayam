package com.rudra.sahayam.domain.model

data class ResourceItem(
    val id: String,
    val name: String,
    val type: String,
    val distanceKm: Double,
    val available: Boolean
)
