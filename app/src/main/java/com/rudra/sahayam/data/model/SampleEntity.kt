package com.rudra.sahayam.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "samples")
data class SampleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
