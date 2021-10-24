package com.klima7.services.common.models

data class Client(
    val uid: String,
    val info: ClientInfo,
    val fromCache: Boolean
)
