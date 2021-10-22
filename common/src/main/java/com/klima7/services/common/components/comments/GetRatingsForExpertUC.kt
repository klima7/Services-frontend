package com.klima7.services.common.components.comments

import com.klima7.services.common.data.repositories.RatingsRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Rating
import com.klima7.services.common.base.BaseUC
import com.klima7.services.common.utils.Outcome

class GetRatingsForExpertUC(
    private val ratingsRepository: RatingsRepository
): BaseUC<GetRatingsForExpertUC.Params, List<Rating>>() {

    data class Params(val expertId: String, val afterId: String?, val count: Int)

    override suspend fun execute(params: Params): Outcome<Failure, List<Rating>> =
        ratingsRepository.getRatingsForExpert(params.expertId, params.afterId, params.count)
}