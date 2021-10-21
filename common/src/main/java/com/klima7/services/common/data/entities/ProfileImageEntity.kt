package com.klima7.services.common.data.entities

import com.google.firebase.Timestamp

data class ProfileImageEntity(
    var url: String = "",
    var changeTime: Timestamp = Timestamp.now()
)
