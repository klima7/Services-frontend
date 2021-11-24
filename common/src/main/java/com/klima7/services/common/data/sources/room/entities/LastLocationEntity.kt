package com.klima7.services.common.data.sources.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LastLocationEntity(
    @PrimaryKey val id: String,
    val placeId: String,
    val placeName: String,
    val time: Long
)
