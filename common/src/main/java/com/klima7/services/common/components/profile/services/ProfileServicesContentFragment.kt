package com.klima7.services.common.components.profile.services

import android.text.SpannableString
import android.text.style.BulletSpan
import com.klima7.services.common.R
import com.klima7.services.common.components.profile.ProfileViewModel
import com.klima7.services.common.databinding.FragmentProfileServicesContentBinding
import com.klima7.services.common.platform.BaseLoadFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileServicesContentFragment: BaseLoadFragment<FragmentProfileServicesContentBinding>() {

    override val layoutId = R.layout.fragment_profile_services_content
    override val viewModel: ProfileServicesViewModel by viewModel()
    private val profileViewModel  by lazy {
        requireParentFragment().requireParentFragment().getViewModel<ProfileViewModel>()
    }

    override fun init() {
        super.init()

        profileViewModel.expert.observe(viewLifecycleOwner) { expert ->
            if(expert != null) {
                viewModel.setServicesIds(expert.servicesIds.toList())
            }
        }

        viewModel.services.observe(viewLifecycleOwner) { services ->
            if(services != null) {
                binding.profileservicesText.text = services.map { it.name }.toBulletedList()
            }
        }
    }

    private fun List<String>.toBulletedList(): CharSequence {
        return SpannableString(this.joinToString("\n")).apply {
            this@toBulletedList.foldIndexed(0) { index, acc, span ->
                val end = acc + span.length + if (index != this@toBulletedList.size - 1) 1 else 0
                this.setSpan(BulletSpan(16), acc, end, 0)
                end
            }
        }
    }
}