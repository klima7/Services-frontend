package com.klima7.services.common.components.msgviewer

import android.view.Gravity
import com.bumptech.glide.Glide
import com.klima7.services.common.R
import com.klima7.services.common.data.di.EMULATE
import com.klima7.services.common.databinding.ElementImageMessageBinding
import com.klima7.services.common.models.ImageMessage
import com.klima7.services.common.ui.convertDpToPixel
import com.xwray.groupie.databinding.BindableItem


class ImageMessageItem(
    private val message: ImageMessage,
    private val side: Side,
    ) : BindableItem<ElementImageMessageBinding>() {

    override fun bind(binding: ElementImageMessageBinding, position: Int) {
        binding.apply {
            if (side == Side.LEFT) {
                msgimageContainer.gravity = Gravity.LEFT
                msgimageContainer.setPadding(0, 0, convertDpToPixel(64f, msgimageContainer.context), 0)
            } else if (side == Side.RIGHT) {
                msgimageContainer.gravity = Gravity.RIGHT
                msgimageContainer.setPadding(convertDpToPixel(64f, msgimageContainer.context), 0, 0, 0)
            }
        }

        val image = binding.msgimageImage
        val fixedUrl = if(EMULATE) message.imageUrl.replace("localhost", "10.0.2.2") else message.imageUrl

        Glide.with(image.context).clear(image)
        Glide.with(image.context)
            .load(fixedUrl)
            .placeholder(R.drawable.avatar_placeholder)
            .error(R.drawable.avatar_failure)
            .into(image)
    }

    override fun getLayout(): Int {
        return R.layout.element_image_message
    }

}