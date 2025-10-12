package com.rudra.sahayam.data.api.response

import com.google.gson.annotations.SerializedName

data class SyncGuestResponse(
    val status: String,
    @SerializedName("migrated_records") val migratedRecords: Int
)
