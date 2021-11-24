package com.klima7.services.common.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.klima7.services.common.data.room.dao.LastLocationDao
import com.klima7.services.common.data.room.entities.LastLocationEntity

@Database(entities = [LastLocationEntity::class], version = 1)
abstract class RoomSource : RoomDatabase() {
    abstract fun lastLocationDao(): LastLocationDao
}
