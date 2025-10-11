package com.rudra.sahayam.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sahayam.app.data.model.SampleEntity
import com.sahayam.app.data.db.dao.SampleDao

@Database(entities = [SampleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sampleDao(): SampleDao
}
