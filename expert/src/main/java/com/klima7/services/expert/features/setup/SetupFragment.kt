package com.klima7.services.expert.features.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.common.lib.failurable.FailurableFragment
import com.klima7.services.common.lib.failurable.FailurableWrapperFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentSetupBinding

class SetupFragment: BaseFragment<FragmentSetupBinding>() {

    override val layoutId = R.layout.fragment_setup
    override val viewModel = BaseViewModel()

    override fun init() {
        binding.setupToolbar.title = "Uzupełnienie informacji"
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        childFragmentManager
            .beginTransaction()
            .add(R.id.setup_content_container_view, FailurableWrapperFragment(SetupContentFragment()))
            .commit()
    }
}