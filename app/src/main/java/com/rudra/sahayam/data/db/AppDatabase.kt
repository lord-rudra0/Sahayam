package com.rudra.sahayam.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rudra.sahayam.domain.model.ChatMessage

@Database(entities = [AlertEntity::class, ReportEntity::class, ChatMessage::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alertDao(): AlertDao
    abstract fun reportDao(): ReportDao
    abstract fun chatMessageDao(): ChatMessageDao
}
