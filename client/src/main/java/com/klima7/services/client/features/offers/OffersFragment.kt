package com.klima7.services.client.features.offers

import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentOffersBinding
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class OffersFragment: BaseFragment<FragmentOffersBinding>() {

    override val layoutId = R.layout.fragment_offers
    override val viewModel: OffersViewModel by viewModel()

    override fun init() {
        super.init()
        binding.offersToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

}