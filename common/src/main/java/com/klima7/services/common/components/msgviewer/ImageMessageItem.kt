package com.klima7.services.common.components.msgviewer

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.signature.ObjectKey
import com.klima7.services.common.R
import com.klima7.services.common.data.di.EMULATE
import com.klima7.services.common.databinding.ElementImageMessageBinding
import com.klima7.services.common.models.ImageMessage
import com.xwray.groupie.databinding.BindableItem


class ImageMessageItem(
    private val message: ImageMessage,
    ) : BindableItem<ElementImageMessageBinding>() {

    override fun bind(binding: ElementImageMessageBinding, position: Int) {
        val image = binding.msgimageImage
        val fixedUrl = if(EMULATE) message.imageUrl.replace("localhost", "10.0.2.2") else message.imageUrl

        Glide.with(image.context).clear(image)
        Glide.with(image.context)
            .load(fixedUrl)
//            .transition(DrawableTransitionOptions.withCrossFade(resources.getInteger(R.integer.avatar_fade_time)))
            .placeholder(R.drawable.avatar_placeholder)
            .error(R.drawable.avatar_failure)
            .into(image)
    }

    override fun getLayout(): Int {
        return R.layout.element_image_message
    }

}