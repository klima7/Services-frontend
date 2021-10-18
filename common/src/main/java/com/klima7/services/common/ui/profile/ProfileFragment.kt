package com.klima7.services.common.ui.profile

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentContainerBinding
import com.klima7.services.common.databinding.FragmentProfileBinding
import com.klima7.services.common.ui.base.BaseFragment
import com.klima7.services.common.ui.base.BaseViewModel
import com.klima7.services.common.ui.loadable.LoadableWrapperFragment
import com.klima7.services.common.ui.utils.replaceFragment

class ProfileFragment: BaseFragment<FragmentContainerBinding>() {

    override val layoutId = R.layout.fragment_container
    override val viewModel = BaseViewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        replaceFragment(R.id.container_container_view, LoadableWrapperFragment(ProfileContentFragment()))
    }
}
