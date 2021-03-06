package com.klima7.services.expert.features.offers.archive

import com.klima7.services.expert.R
import com.klima7.services.expert.features.offers.base.BaseOffersListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArchiveOffersListFragment: BaseOffersListFragment(
    R.color.swipe_archive_offer_background,
    R.drawable.icon_offers_current,
    R.string.offers__move_to_current_failure,
) {

    override val viewModel: ArchiveOffersListViewModel by viewModel()

}