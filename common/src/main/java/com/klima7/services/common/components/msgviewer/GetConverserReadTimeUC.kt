package com.klima7.services.common.components.msgviewer

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.OffersRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Offer
import com.klima7.services.common.models.Role
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.util.*

class GetConverserReadTimeUC(
    private val offersRepository: OffersRepository,
): BaseUC<GetConverserReadTimeUC.Params, Flow<Date?>>() {

    data class Params(val role: Role, val offerId: String)

    override suspend fun execute(params: Params): Outcome<Failure, Flow<Date?>> {
        val flow = offersRepository
            .getOfferStream(params.offerId)
            .map { offer -> getReadTime(params.role, offer) }
            .distinctUntilChanged()
        return Outcome.Success(flow)
    }

    private fun getReadTime(role: Role, offer: Offer): Date? {
        return if(role == Role.CLIENT) {
            offer.expertReadTime
        } else {
            offer.clientReadTime
        }
    }
}