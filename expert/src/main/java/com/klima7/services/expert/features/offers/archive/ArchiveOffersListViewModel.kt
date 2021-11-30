package com.klima7.services.expert.features.offers.archive

import com.klima7.services.expert.features.offers.base.BaseOffersListViewModel
import com.klima7.services.expert.features.offers.base.SetOfferArchivedUC

class ArchiveOffersListViewModel(
    getArchiveOffersForCurrentExpertUC: GetArchiveOffersForCurrentExpertUC,
    setOfferArchivedUC: SetOfferArchivedUC,
): BaseOffersListViewModel(getArchiveOffersForCurrentExpertUC, setOfferArchivedUC, false)