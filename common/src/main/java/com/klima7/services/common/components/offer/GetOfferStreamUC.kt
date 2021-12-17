package com.klima7.services.common.components.offer

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.OffersRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Offer
import kotlinx.coroutines.flow.Flow

class GetOfferStreamUC(
    private val offersRepository: OffersRepository
): BaseUC<GetOfferStreamUC.Params, Flow<Offer>>() {

    data class Params(val offerId: String)

    override suspend fun execute(params: Params): Outcome<Failure, Flow<Offer>> {
        return Outcome.Success(offersRepository.getOfferStream(params.offerId))
    }
}