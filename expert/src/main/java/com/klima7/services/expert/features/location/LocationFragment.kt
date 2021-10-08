package com.klima7.services.expert.features.location

import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.common.lib.failurable.FailurableWrapperFragment
import com.klima7.services.common.lib.utils.replaceFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentToolbarBinding

class LocationFragment: BaseFragment<FragmentToolbarBinding>() {

    override val layoutId = R.layout.fragment_toolbar
    override val viewModel = BaseViewModel()

    override fun init() {
        val toolbar = binding.toolbarToolbar
        toolbar.title = "Zmiana lokalizacji"
        toolbar.setNavigationIcon(R.drawable.icon_arrow_back)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        replaceFragment(R.id.toolbar_container_view, FailurableWrapperFragment(LocationContentFragment()))
    }
}