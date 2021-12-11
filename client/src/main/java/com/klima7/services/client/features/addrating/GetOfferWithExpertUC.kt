package com.klima7.services.client.features.addrating

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.data.repositories.OffersRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Offer
import com.klima7.services.common.models.OfferWithExpert

class GetOfferWithExpertUC(
    private val offersRepository: OffersRepository,
    private val expertsRepository: ExpertsRepository
): BaseUC<GetOfferWithExpertUC.Params, OfferWithExpert>() {

    data class Params(val offerId: String)

    override suspend fun execute(params: Params): Outcome<Failure, OfferWithExpert> {
        return getOfferPart(params.offerId)
    }

    private suspend fun getOfferPart(offerId: String): Outcome<Failure, OfferWithExpert> {
        return offersRepository.getOffer(offerId).foldS({ failure ->
            Outcome.Failure(failure)
        }, { offer ->
            getExpertPart(offer)
        })
    }

    private suspend fun getExpertPart(offer: Offer): Outcome<Failure, OfferWithExpert> {
        val expertId = offer.expertId ?: return Outcome.Success(OfferWithExpert(offer, null))
        return expertsRepository.getExpert(expertId).foldS({ failure ->
            Outcome.Failure(failure)
        }, { expert ->
            Outcome.Success(OfferWithExpert(offer, expert))
        })
    }
}