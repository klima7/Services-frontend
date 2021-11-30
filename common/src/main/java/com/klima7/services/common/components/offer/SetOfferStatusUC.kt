package com.klima7.services.common.components.offer

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.data.repositories.OffersRepository
import com.klima7.services.common.models.OfferStatus

class SetOfferStatusUC(
    private val offersRepository: OffersRepository
): BaseUC<SetOfferStatusUC.Params, None>() {

    data class Params(val offerId: String, val offerStatus: OfferStatus)

    override suspend fun execute(params: Params) =
        offersRepository.setOfferStatus(params.offerId, params.offerStatus)
}