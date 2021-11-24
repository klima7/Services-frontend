package com.klima7.services.common.data.repositories

import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.sources.room.RoomSource
import com.klima7.services.common.data.sources.room.converters.toRoomEntity
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.LastLocation

class LastLocationsRepository(
    room: RoomSource
) {

    private val lastMessagesDao = room.lastLocationDao()

    fun addLocation(location: LastLocation): Outcome<Failure, None> {
        lastMessagesDao.insert(location.toRoomEntity())
        return Outcome.Success(None())
    }

}