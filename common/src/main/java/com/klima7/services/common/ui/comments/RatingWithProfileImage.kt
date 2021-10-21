package com.klima7.services.common.ui.comments

import com.klima7.services.common.domain.models.ProfileImage
import com.klima7.services.common.domain.models.Rating

data class RatingWithProfileImage(
    val rating: Rating,
    val profileImage: ProfileImage?
)
