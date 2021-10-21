package com.klima7.services.common.ui.rating

import com.klima7.services.common.domain.models.ProfileImage
import com.klima7.services.common.domain.models.Rating

data class RatingWithProfileImage(
    val rating: Rating,
    val profileImage: ProfileImage?
)
