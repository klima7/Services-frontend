package com.klima7.services.expert.features.offers.archive

import com.klima7.services.expert.features.offers.base.BaseOffersListViewModel

class ArchiveOffersListViewModel(
    getArchiveOffersForCurrentExpertUC: GetArchiveOffersForCurrentExpertUC,
    moveOfferToCurrentUC: MoveOfferToCurrentUC,
): BaseOffersListViewModel(getArchiveOffersForCurrentExpertUC, moveOfferToCurrentUC)