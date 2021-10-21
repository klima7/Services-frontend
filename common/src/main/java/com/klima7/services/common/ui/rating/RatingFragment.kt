package com.klima7.services.common.ui.rating

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentRatingBinding
import com.klima7.services.common.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RatingFragment: BaseFragment<FragmentRatingBinding>() {

    override val layoutId = R.layout.fragment_rating
    override val viewModel: RatingViewModel by viewModel()

}