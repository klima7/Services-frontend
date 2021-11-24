package com.klima7.services.common.data.sources.firebase.converters

import com.klima7.services.common.data.sources.firebase.entities.ProfileImageEntity
import com.klima7.services.common.models.ProfileImage

fun ProfileImageEntity.toDomain(): ProfileImage {
    return ProfileImage(url, changeTime.seconds)
}
