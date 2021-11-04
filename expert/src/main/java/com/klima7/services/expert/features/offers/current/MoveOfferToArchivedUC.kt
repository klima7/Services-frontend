package com.klima7.services.expert.features.offers.current

import android.util.Log
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.OffersRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.expert.features.offers.base.MoveOfferUC
import kotlinx.coroutines.delay

class MoveOfferToArchivedUC(
    private val offersRepository: OffersRepository
): MoveOfferUC() {

    override suspend fun execute(params: Params): Outcome<Failure, None> {
        delay(2000)
        Log.i("Hello", "Offer moved to archived")
        return Outcome.Success(None())
    }
}