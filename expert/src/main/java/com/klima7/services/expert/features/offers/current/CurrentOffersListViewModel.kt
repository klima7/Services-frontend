package com.klima7.services.expert.features.offers.current

import com.klima7.services.expert.features.offers.base.BaseOffersListViewModel
import com.klima7.services.expert.usecases.GetCurrentExpertServicesUC
import com.klima7.services.expert.usecases.SetOfferArchivedUC

class CurrentOffersListViewModel(
    getCurrentOffersForCurrentExpertUC: GetCurrentOffersForCurrentExpertUC,
    setOfferArchivedUC: SetOfferArchivedUC,
    getCurrentExpertServicesUC: GetCurrentExpertServicesUC,
): BaseOffersListViewModel(getCurrentOffersForCurrentExpertUC, setOfferArchivedUC,
    getCurrentExpertServicesUC, true)
