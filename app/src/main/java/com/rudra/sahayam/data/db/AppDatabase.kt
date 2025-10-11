package com.rudra.sahayam.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rudra.sahayam.data.model.SampleEntity
import com.rudra.sahayam.data.db.dao.SampleDao



@Database(entities = [SampleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sampleDao(): SampleDao
}
