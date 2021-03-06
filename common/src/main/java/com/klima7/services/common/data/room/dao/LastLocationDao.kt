package com.klima7.services.common.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.klima7.services.common.data.room.entities.LastLocationEntity

@Dao
interface LastLocationDao {

    @Query("SELECT * FROM LastLocationEntity ORDER BY time DESC LIMIT :limit")
    fun getLast(limit: Int): List<LastLocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(location: LastLocationEntity)

}
