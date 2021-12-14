package com.klima7.services.expert.features.offers.archive

import com.klima7.services.expert.features.offers.base.BaseOffersListViewModel
import com.klima7.services.expert.usecases.GetCurrentExpertServicesUC
import com.klima7.services.expert.usecases.SetOfferArchivedUC

class ArchiveOffersListViewModel(
    getArchiveOffersForCurrentExpertUC: GetArchiveOffersForCurrentExpertUC,
    setOfferArchivedUC: SetOfferArchivedUC,
    getCurrentExpertServicesUC: GetCurrentExpertServicesUC,
): BaseOffersListViewModel(getArchiveOffersForCurrentExpertUC, setOfferArchivedUC,
    getCurrentExpertServicesUC, false)