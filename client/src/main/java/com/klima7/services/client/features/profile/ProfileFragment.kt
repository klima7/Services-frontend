package com.klima7.services.client.features.profile

import androidx.lifecycle.map
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentProfileBinding
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment: BaseFragment<FragmentProfileBinding>() {

    override val layoutId = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        val expertUid = arguments?.getString("expertUid") ?: throw Error("expertUid argument not supplied")
        childFragmentManager.findFragmentById(R.id.profile_fragment)
            ?.getViewModel<ProfileContentViewModel>()?.start(expertUid)
    }

    override fun init() {
        super.init()

        binding.profileToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        childFragmentManager.findFragmentById(R.id.profile_fragment)
            ?.getViewModel<ProfileContentViewModel>()?.expert?.map { expert -> expert.info.name }
            ?.observe(viewLifecycleOwner, { expertName ->
                binding.profileToolbar.subtitle = expertName
            })
    }
}