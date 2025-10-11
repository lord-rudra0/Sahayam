package com.rudra.sahayam.domain.model

// Wrapper class to match the API response { "resources": [...] }
data class ResourceResponse(
    val resources: List<ResourceItem>
)

data class ResourceItem(
    val id: String,
    val type: String,
    val name: String,
    val quantity: Int,
    val available: Boolean,
    val location: Location
)
