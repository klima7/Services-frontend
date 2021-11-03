package com.klima7.services.expert.features.offer

import android.util.Log
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentOfferBinding
import com.klima7.services.expert.features.job.JobFragment

class OfferFragment: BaseFragment<FragmentOfferBinding>() {

    override val layoutId = R.layout.fragment_offer
    override val viewModel = BaseViewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()

        val offerId = arguments?.getString("offerId") ?: throw Error("JobId argument not supplied")
        Log.i("Hello", "OfferActivity launched with id: $offerId")
    }

    override fun init() {
        super.init()
        binding.offerToolbar.setNavigationOnClickListener { requireActivity().finish() }
    }

}