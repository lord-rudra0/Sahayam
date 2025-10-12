package com.rudra.sahayam.data.api.request

import com.google.gson.annotations.SerializedName

// Placeholder for local data, can be fleshed out later
data class LocalReport(val id: String, val content: String)
data class LocalMessage(val id: String, val text: String)

data class SyncGuestRequest(
    @SerializedName("guest_id") val guestId: String,
    @SerializedName("user_id") val userId: String,
    @SerializedName("local_reports") val localReports: List<LocalReport>,
    @SerializedName("local_messages") val localMessages: List<LocalMessage>
)
