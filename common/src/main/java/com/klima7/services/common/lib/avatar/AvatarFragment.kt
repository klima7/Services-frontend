package com.klima7.services.common.lib.avatar

import com.bumptech.glide.Glide
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentAvatarBinding
import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.common.lib.base.BaseViewModel


class AvatarFragment: BaseFragment<FragmentAvatarBinding>() {

    override val layoutId = R.layout.fragment_avatar
    override val viewModel = BaseViewModel()

    fun setAvatarUri(uri: String) {
        Glide.with(this)
            .load(uri)
            .placeholder(R.drawable.image_profile_default)
            .error(R.drawable.image_profile_default)
            .centerCrop()
            .into(binding.avatarImage)
    }

}