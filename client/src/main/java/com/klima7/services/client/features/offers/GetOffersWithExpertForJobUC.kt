package com.klima7.services.client.features.offers

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.data.repositories.OffersRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Offer
import com.klima7.services.common.models.OfferWithExpert

class GetOffersWithExpertForJobUC(
    private val offersRepository: OffersRepository,
    private val expertsRepository: ExpertsRepository
): BaseUC<GetOffersWithExpertForJobUC.Params, List<OfferWithExpert>>() {

    data class Params(val jobId: String)

    override suspend fun execute(params: Params): Outcome<Failure, List<OfferWithExpert>> {
        return getOffersPart(params.jobId)
    }

    private suspend fun getOffersPart(jobId: String): Outcome<Failure, List<OfferWithExpert>> {
        return offersRepository.getOffersForJob(jobId).foldS({ failure ->
            Outcome.Failure(failure)
        }, { offers ->
            getExpertsPart(offers)
        })
    }

    private suspend fun getExpertsPart(offers: List<Offer>): Outcome<Failure, List<OfferWithExpert>> {
        val results = mutableListOf<OfferWithExpert>()
        for(offer in offers) {
            when(val outcome = expertsRepository.getExpert(offer.expertId)) {
                is Outcome.Success -> {
                    val result = OfferWithExpert(offer, outcome.b)
                    results.add(result)
                }
                is Outcome.Failure -> {
                    if(outcome.a !is Failure.InternetFailure) {
                        return Outcome.Failure(outcome.a);
                    }
                }
            }
        }
        return Outcome.Success(results)
    }
}