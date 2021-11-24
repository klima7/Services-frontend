package com.klima7.services.common.data.sources.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.klima7.services.common.data.sources.room.dao.LastLocationDao
import com.klima7.services.common.data.sources.room.entities.LastLocationEntity

@Database(entities = [LastLocationEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lastLocationDao(): LastLocationDao
}
