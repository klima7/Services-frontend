package com.klima7.services.client.features.addcomm

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.data.repositories.RatingsRepository

class AddRatingUC(
    private val ratingsRepository: RatingsRepository
): BaseUC<AddRatingUC.Params, None>() {

    data class Params(val offerId: String, val rating: Double, val comment: String?)

    override suspend fun execute(params: Params) =
        ratingsRepository.addRating(params.offerId, params.rating, params.comment)
}