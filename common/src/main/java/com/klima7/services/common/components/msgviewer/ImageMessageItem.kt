package com.klima7.services.common.components.msgviewer

import com.klima7.services.common.R
import com.klima7.services.common.databinding.ElementImageMessageBinding
import com.klima7.services.common.models.ImageMessage
import com.xwray.groupie.databinding.BindableItem


class ImageMessageItem(
    private val message: ImageMessage,
    ) : BindableItem<ElementImageMessageBinding>() {

    override fun bind(binding: ElementImageMessageBinding, position: Int) {
        binding.msgimageUrl.text = message.imageUrl
    }

    override fun getLayout(): Int {
        return R.layout.element_image_message
    }

}