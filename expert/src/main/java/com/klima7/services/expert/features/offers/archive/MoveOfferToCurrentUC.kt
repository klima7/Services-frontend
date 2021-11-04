package com.klima7.services.expert.features.offers.archive

import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.OffersRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.expert.features.offers.base.MoveOfferUC

class MoveOfferToCurrentUC(
    private val offersRepository: OffersRepository
): MoveOfferUC() {

    override suspend fun execute(params: Params): Outcome<Failure, None> =
        offersRepository.setOfferArchived(params.offerId, false)
}