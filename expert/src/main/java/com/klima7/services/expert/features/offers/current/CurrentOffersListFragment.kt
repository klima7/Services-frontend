package com.klima7.services.expert.features.offers.current

import com.klima7.services.expert.R
import com.klima7.services.expert.features.offers.base.BaseOffersListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrentOffersListFragment: BaseOffersListFragment(
    R.color.swipe_current_offer_background,
    R.drawable.icon_offers_archive
) {

    override val viewModel: CurrentOffersListViewModel by viewModel()

}