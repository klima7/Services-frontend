package com.klima7.services.common.components.profile.services

import android.text.SpannableString
import android.text.style.BulletSpan
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileServicesBinding
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileServicesFragment: BaseFragment<FragmentProfileServicesBinding>() {

    override val layoutId = R.layout.fragment_profile_services
    override val viewModel: ProfileServicesViewModel by viewModel()

    override fun init() {
        super.init()

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