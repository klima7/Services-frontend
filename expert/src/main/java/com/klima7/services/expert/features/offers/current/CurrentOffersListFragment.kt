package com.klima7.services.expert.features.offers.current

import com.klima7.services.expert.features.offers.base.BaseOffersListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrentOffersListFragment: BaseOffersListFragment() {

    override val viewModel: CurrentOffersListViewModel by viewModel()

}