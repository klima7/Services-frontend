package com.klima7.services.common.lib.avatar

import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.klima7.services.common.R
import com.klima7.services.common.data.di.EMULATE
import com.klima7.services.common.databinding.FragmentAvatarBinding
import com.klima7.services.common.lib.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AvatarFragment : BaseFragment<FragmentAvatarBinding>() {

    override val layoutId = R.layout.fragment_avatar
    override val viewModel: AvatarViewModel by viewModel()

    override fun init() {
        super.init()
        viewModel.imageData.observe(viewLifecycleOwner) { imageData ->
            if(imageData != null) {
                updateImage(imageData)
            }
        }
    }

    fun setUri(uri: String) {
        viewModel.setUri(uri)
    }

    private fun updateImage(imageData: AvatarViewModel.ImageData) {
        val fixedUri = if(EMULATE) imageData.uri.replace("localhost", "10.0.2.2") else imageData.uri
        val image = binding.avatarImage

        Glide.with(this).clear(image)

        Glide.with(this)
            .load(fixedUri)
            .signature(ObjectKey(imageData.signature))
            .placeholder(R.drawable.image_profile_default)
            .error(R.drawable.image_profile_default)
            .into(image)
    }
}
