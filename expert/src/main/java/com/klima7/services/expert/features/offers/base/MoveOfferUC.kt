package com.klima7.services.expert.features.offers.base

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None

abstract class MoveOfferUC: BaseUC<MoveOfferUC.Params, None>() {

    data class Params(val offerId: String)

}