package com.klima7.services.common.lib.avatar

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.signature.ObjectKey
import com.klima7.services.common.R
import com.klima7.services.common.data.di.EMULATE
import com.klima7.services.common.databinding.FragmentAvatarBinding
import com.klima7.services.common.domain.models.ProfileImage
import com.klima7.services.common.lib.GlideApp
import com.klima7.services.common.lib.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AvatarFragment : BaseFragment<FragmentAvatarBinding>() {

    override val layoutId = R.layout.fragment_avatar
    override val viewModel: AvatarViewModel by viewModel()

    override fun init() {
        super.init()
        viewModel.profileImage.observe(viewLifecycleOwner) { profileImage ->
            if(profileImage != null) {
                updateProfileImage(profileImage)
            }
        }
    }

    fun setProfileImage(profileImage: ProfileImage?) {
        viewModel.setProfileImage(profileImage)
    }

    private fun updateProfileImage(profileImage: ProfileImage) {
        val fixedUri = if(EMULATE) profileImage.url.replace("localhost", "10.0.2.2") else profileImage.url
        val image = binding.avatarImage

        Glide.with(this).clear(image)

        Glide.with(this)
            .load(fixedUri)
            .transition(withCrossFade(resources.getInteger(R.integer.avatar_fade_time)))
            .signature(ObjectKey(profileImage.changeTime))
            .placeholder(R.drawable.image_profile_default)
            .error(R.drawable.image_profile_default)
            .into(image)
    }
}
