package com.klima7.services.common.components.rating

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentRatingBinding
import com.klima7.services.common.models.Rating
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RatingFragment: BaseFragment<FragmentRatingBinding>() {

    override val layoutId = R.layout.fragment_rating
    override val viewModel: RatingViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        val ratingId = arguments?.getString("ratingId") ?: throw Error("ratingId argument not supplied")
        val rating = arguments?.getParcelable<Rating>("rating")
        viewModel.start(ratingId, rating)
    }

    override fun init() {
        super.init()
        binding.ratingToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

}