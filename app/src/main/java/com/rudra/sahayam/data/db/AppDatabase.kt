package com.rudra.sahayam.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AlertEntity::class, ReportEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alertDao(): AlertDao
    abstract fun reportDao(): ReportDao
}
