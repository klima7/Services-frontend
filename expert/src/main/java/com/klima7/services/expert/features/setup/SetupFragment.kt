package com.klima7.services.expert.features.setup

import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.common.lib.loadable.LoadableWrapperFragment
import com.klima7.services.common.lib.utils.replaceFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentToolbarBinding

class SetupFragment: BaseFragment<FragmentToolbarBinding>() {

    override val layoutId = R.layout.fragment_toolbar
    override val viewModel = BaseViewModel()

    override fun init() {
        binding.toolbarToolbar.title = "Uzupełnienie danych"
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        replaceFragment(R.id.toolbar_container_view, LoadableWrapperFragment(SetupContentFragment()))
    }
}
