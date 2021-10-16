package com.klima7.services.common.domain.models

data class Expert(
    val uid: String,
    val info: ExpertInfo,
    val profileImage: ProfileImage?,
    val area: WorkingArea?,
    val servicesIds: Set<String>,
    val ratingsCount: Int,
    val commentsCount: Int,
    val rating: Double,
    val active: Boolean,
    val fromCache: Boolean
)
