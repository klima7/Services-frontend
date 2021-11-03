package com.klima7.services.expert.features.offers.current

import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.OffersRepository
import com.klima7.services.expert.features.offers.base.GetOffersForCurrentExpertUC

class GetCurrentOffersForCurrentExpertUC(
    authRepository: AuthRepository,
    offersRepository: OffersRepository
): GetOffersForCurrentExpertUC(authRepository, offersRepository) {
    override val archived = false
}