package com.klima7.services.common.domain.models

data class Expert(
    val uid: String,
    val info: ExpertInfo,
    val area: WorkingArea?,
    val services: Set<Service>,
    val ratingsCount: Int,
    val commentsCount: Int,
    val rating: Double,
    val active: Boolean,
    val fromCache: Boolean
)