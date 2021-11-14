package com.klima7.services.common.components.profile

import com.klima7.services.common.R
import com.klima7.services.common.components.profile.area.ProfileAreaViewModel
import com.klima7.services.common.components.profile.comments.ProfileCommentsViewModel
import com.klima7.services.common.components.profile.contact.ProfileContactViewModel
import com.klima7.services.common.components.profile.description.ProfileDescriptionViewModel
import com.klima7.services.common.components.profile.header.ProfileHeaderViewModel
import com.klima7.services.common.components.profile.rating.ProfileRatingViewModel
import com.klima7.services.common.components.profile.services.ProfileServicesViewModel
import com.klima7.services.common.databinding.FragmentBaseProfileBinding
import com.klima7.services.common.models.Expert
import com.klima7.services.common.platform.BaseLoadFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel

abstract class BaseProfileFragment: BaseLoadFragment<FragmentBaseProfileBinding>() {

    override val layoutId = R.layout.fragment_base_profile
    abstract override val viewModel: BaseProfileViewModel

    override fun init() {
        super.init()

        binding.profileRefreshLayout.apply {
            setOnRefreshListener {
                isRefreshing = false
                viewModel.refresh()
            }
        }

        viewModel.expert.observe(viewLifecycleOwner) { expert ->
            updateExpert(expert)
        }
    }

    private fun updateExpert(expert: Expert) {
        childFragmentManager.apply {
            findFragmentById(R.id.profile_header_fragment)
                ?.getViewModel<ProfileHeaderViewModel>()?.setExpert(expert)
            findFragmentById(R.id.profile_description_fragment)
                ?.getViewModel<ProfileDescriptionViewModel>()?.setExpert(expert)
            findFragmentById(R.id.profile_contact_fragment)
                ?.getViewModel<ProfileContactViewModel>()?.setInfo(expert.info)
            findFragmentById(R.id.profile_services_fragment)
                ?.getViewModel<ProfileServicesViewModel>()?.setServicesIds(expert.servicesIds.toList())
            findFragmentById(R.id.profile_area_fragment)
                ?.getViewModel<ProfileAreaViewModel>()?.setArea(expert.area)
            findFragmentById(R.id.profile_rating_fragment)
                ?.getViewModel<ProfileRatingViewModel>()?.setExpert(expert)
            findFragmentById(R.id.profile_comments_fragment)
                ?.getViewModel<ProfileCommentsViewModel>()?.setExpert(expert)
        }
    }
}