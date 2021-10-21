package com.klima7.services.common.ui.comments

import com.klima7.services.common.data.repositories.ProfileImagesRepository
import com.klima7.services.common.data.repositories.RatingsRepository
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.models.Rating
import com.klima7.services.common.domain.utils.BaseUC
import com.klima7.services.common.domain.utils.Outcome
import com.klima7.services.common.ui.rating.RatingWithProfileImage

class GetExpertRatingsWithProfileImagesUC(
    private val ratingsRepository: RatingsRepository,
    private val profileImagesRepository: ProfileImagesRepository,
): BaseUC<GetExpertRatingsWithProfileImagesUC.Params, List<RatingWithProfileImage>>() {

    data class Params(val expertId: String, val afterId: String?, val count: Int)

    override suspend fun execute(params: Params): Outcome<Failure, List<RatingWithProfileImage>> {
        return getRatingsPart(params.expertId, params.afterId, params.count)
    }

    private suspend fun getRatingsPart(expertId: String, afterId: String?, count: Int):
            Outcome<Failure, List<RatingWithProfileImage>> {
        return ratingsRepository.getRatingsForExpert(expertId, afterId, count).foldS({ failure ->
            Outcome.Failure(failure)
        }, { ratings ->
            getProfileImagesPart(ratings)
        })
    }

    private suspend fun getProfileImagesPart(ratings: List<Rating>):
            Outcome<Failure, List<RatingWithProfileImage>> {
        val results = mutableListOf<RatingWithProfileImage>()

        for(rating in ratings) {
            val expertId = rating.expertId
            when(val outcome = profileImagesRepository.getProfileImage(expertId)) {
                is Outcome.Failure -> return Outcome.Failure(outcome.a)
                is Outcome.Success -> {
                    val profileImage = outcome.b
                    val result = RatingWithProfileImage(rating, profileImage)
                    results.add(result)
                }
            }
        }

        return Outcome.Success(results)
    }
}