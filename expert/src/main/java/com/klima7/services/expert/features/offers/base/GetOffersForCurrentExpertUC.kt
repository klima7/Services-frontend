package com.klima7.services.expert.features.offers.base

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.OffersRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Offer

abstract class GetOffersForCurrentExpertUC(
    private val authRepository: AuthRepository,
    private val offersRepository: OffersRepository
): BaseUC<GetOffersForCurrentExpertUC.Params, List<Offer>>() {

    abstract val archived: Boolean

    data class Params(val afterId: String?, val count: Int)

    override suspend fun execute(params: Params): Outcome<Failure, List<Offer>> {
        return getUidPart(params.afterId, params.count)
    }

    private suspend fun getUidPart(afterId: String?, count: Int): Outcome<Failure, List<Offer>> {
        return authRepository.getUid().foldS({ failure ->
            Outcome.Failure(failure)
        }, { uid ->
            if(uid == null) {
                return@foldS Outcome.Failure(Failure.PermissionFailure)
            }
            getOffersPart(uid, afterId, count)
        })
    }

    private suspend fun getOffersPart(uid: String, afterId: String?, count: Int) =
        offersRepository.getOffersForExpert(uid, archived, afterId, count)
}