package com.klima7.services.common.data.sources.firebase.entities

import com.google.firebase.Timestamp

data class ProfileImageEntity(
    var url: String = "",
    var changeTime: Timestamp = Timestamp.now()
)
