package com.rudra.sahayam.data.api.response

// Used for simple status responses like logout, update, etc.
data class GenericStatusResponse(
    val status: String,
    val message: String? = null
)
