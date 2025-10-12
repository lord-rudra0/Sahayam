package com.rudra.sahayam.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {

    @Query("SELECT * FROM alerts ORDER BY timestamp DESC")
    fun getAlerts(): Flow<List<AlertEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlerts(alerts: List<AlertEntity>)

    @Query("DELETE FROM alerts")
    suspend fun deleteAllAlerts()

    @Transaction
    suspend fun cacheAlerts(alerts: List<AlertEntity>) {
        deleteAllAlerts()
        insertAlerts(alerts)
    }
}
