package com.klima7.services.common.data.repositories

import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.FirebaseSource
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Offer
import com.klima7.services.common.models.OfferStatus
import kotlinx.coroutines.flow.Flow

class OffersRepository(
    private val firebase: FirebaseSource,
) {

    suspend fun getOffersForExpert(expertId: String, archived: Boolean, afterId: String?, count: Int):
            Outcome<Failure, List<Offer>> {
            return firebase.offersDao.getOffersForExpert(expertId, archived, afterId, count)
    }

    suspend fun getOffersForJob(jobId: String): Outcome<Failure, List<Offer>> {
        return firebase.offersDao.getOffersForJob(jobId)
    }

    suspend fun getOffer(id: String): Outcome<Failure, Offer> {
        return firebase.offersDao.getOffer(id)
    }
    
    suspend fun getOfferStream(offerId: String): Flow<Offer> {
        return firebase.offersDao.getOfferStream(offerId)
    }

    suspend fun setOfferArchived(offerId: String, archived: Boolean): Outcome<Failure, None> {
        return firebase.offersDao.setOfferArchived(offerId, archived)
    }

    suspend fun setOfferStatus(offerId: String, offerStatus: OfferStatus): Outcome<Failure, None> {
        return firebase.offersDao.setOfferStatus(offerId, offerStatus)
    }

}
