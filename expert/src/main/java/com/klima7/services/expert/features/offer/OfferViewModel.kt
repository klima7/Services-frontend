package com.klima7.services.expert.features.offer

import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.components.offer.BaseOfferViewModel
import com.klima7.services.common.components.offer.SetOfferStatusUC
import com.klima7.services.common.models.Offer

class OfferViewModel(
    setOfferStatusUC: SetOfferStatusUC,
): BaseOfferViewModel(setOfferStatusUC) {

    override val offer = MutableLiveData<Offer>()

}