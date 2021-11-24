package com.klima7.services.common.data.repositories

import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.room.RoomSource
import com.klima7.services.common.data.room.converters.toDomain
import com.klima7.services.common.data.room.converters.toRoomEntity
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.LastLocation
import java.lang.Exception

class LastLocationsRepository(
    room: RoomSource
) {

    private val lastLocationsDao = room.lastLocationDao()

    fun addLocationOrUpdate(location: LastLocation): Outcome<Failure, None> {
        return try {
            lastLocationsDao.insertOrUpdate(location.toRoomEntity())
            return Outcome.Success(None())
        } catch(e: Exception) {
            Outcome.Failure(Failure.UnknownFailure)
        }
    }

    fun getLastLocations(limit: Int): Outcome<Failure, List<LastLocation>> {
        return try {
            val entities = lastLocationsDao.getLast(limit)
            val locations = entities.map { entity -> entity.toDomain() }
            Outcome.Success(locations)
        } catch(e: Exception) {
            Outcome.Failure(Failure.UnknownFailure)
        }
    }

}