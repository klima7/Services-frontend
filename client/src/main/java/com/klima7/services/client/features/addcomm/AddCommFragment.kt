package com.klima7.services.client.features.addcomm

import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentAddCommBinding
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddCommFragment: BaseFragment<FragmentAddCommBinding>() {

    override val layoutId = R.layout.fragment_add_comm
    override val viewModel: AddCommViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        val offerId = arguments?.getString("offerId") ?: throw Error("offerId argument not supplied")
        viewModel.start(offerId)
    }

    override fun init() {
        binding.addcommToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
//        binding.addcommRatingBar.setRating(4.0f)
    }

}