package com.klima7.services.client.features.newjob.category

import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentCategoryBinding
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class CategoryFragment: BaseFragment<FragmentCategoryBinding>() {

    override val layoutId = R.layout.fragment_category
    override val viewModel: CategoryViewModel by viewModel()

}