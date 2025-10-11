package com.rudra.sahayam.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rudra.sahayam.data.model.SampleEntity

@Dao
interface SampleDao {
    @Insert
    suspend fun insert(entity: SampleEntity)

    @Query("SELECT * FROM samples")
    suspend fun getAll(): List<SampleEntity>
}
