package com.klima7.services.common.components.comment

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.data.repositories.RatingsRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Rating
import com.klima7.services.common.models.RatingWithExpert

class GetRatingWithExpertUC(
    private val ratingsRepository: RatingsRepository,
    private val expertsRepository: ExpertsRepository,
): BaseUC<GetRatingWithExpertUC.Params, RatingWithExpert>() {

    data class Params(val ratingId: String)

    override suspend fun execute(params: Params): Outcome<Failure, RatingWithExpert> {
        return getRatingPart(params.ratingId)
    }

    private suspend fun getRatingPart(ratingId: String): Outcome<Failure, RatingWithExpert> {
        return ratingsRepository.getRating(ratingId).foldS({ failure ->
            Outcome.Failure(failure)
        }, { rating ->
            getExpertPart(rating)
        })
    }

    private suspend fun getExpertPart(rating: Rating): Outcome<Failure, RatingWithExpert> {
        return expertsRepository.getExpert(rating.expertId).foldS({ failure ->
            Outcome.Failure(failure)
        }, { expert ->
            Outcome.Success(RatingWithExpert(rating, expert))
        })
    }
}
