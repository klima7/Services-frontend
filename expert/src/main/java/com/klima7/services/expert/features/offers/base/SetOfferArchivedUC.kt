package com.klima7.services.expert.features.offers.base

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.data.repositories.OffersRepository

class SetOfferArchivedUC(
    private val offersRepository: OffersRepository,
): BaseUC<SetOfferArchivedUC.Params, None>() {

    data class Params(val offerId: String, val archived: Boolean)

    override suspend fun execute(params: Params) =
        offersRepository.setOfferArchived(params.offerId, params.archived)
}
