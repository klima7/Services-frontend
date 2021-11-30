package com.klima7.services.expert.features.offers.current

import com.klima7.services.expert.features.offers.base.BaseOffersListViewModel
import com.klima7.services.expert.usecases.SetOfferArchivedUC

class CurrentOffersListViewModel(
    getCurrentOffersForCurrentExpertUC: GetCurrentOffersForCurrentExpertUC,
    setOfferArchivedUC: SetOfferArchivedUC,
): BaseOffersListViewModel(getCurrentOffersForCurrentExpertUC, setOfferArchivedUC, true)
