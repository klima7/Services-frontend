package com.klima7.services.common.components.comment

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.data.repositories.RatingsRepository
import com.klima7.services.common.models.Rating

class GetRatingUC(
    private val ratingsRepository: RatingsRepository,
): BaseUC<GetRatingUC.Params, Rating>() {

    data class Params(val commentId: String)

    override suspend fun execute(params: Params) = ratingsRepository.getRating(params.commentId)
}