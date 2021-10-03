package com.klima7.services.common.models

data class Expert(
    val info: ExpertInfo,
    val profileImageUrl: String?,
    val area: WorkingArea?,
    val services: Set<Service>,
    val ratingsCount: Int,
    val commentsCount: Int,
    val rating: Double,
    val active: Boolean,
    val fromCache: Boolean
)
