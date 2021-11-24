package com.klima7.services.common.data.repositories

import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.FirebaseSource
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Rating

class RatingsRepository(
    private val firebase: FirebaseSource,
) {

    suspend fun getRatingsForExpert(expertId: String, afterId: String?, count: Int):
            Outcome<Failure, List<Rating>> {
        return firebase.ratingsDao.getRatingsForExpert(expertId, afterId, count)
    }

    suspend fun addRating(offerId: String, rating: Double, comment: String?): Outcome<Failure, None> {
        return firebase.ratingsDao.addRating(offerId, rating, comment)
    }

}