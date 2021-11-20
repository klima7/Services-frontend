package com.klima7.services.common.components.msgviewer

import android.content.Context
import android.content.res.TypedArray
import android.util.TypedValue
import android.view.Gravity
import com.bumptech.glide.Glide
import com.klima7.services.common.R
import com.klima7.services.common.data.di.EMULATE
import com.klima7.services.common.databinding.ElementImageMessageBinding
import com.klima7.services.common.models.ImageMessage
import com.klima7.services.common.ui.UrlUtils
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
                binding.cardColor = binding.msgimageCard.context.resources.getColor(R.color.left_message_cloud_color, null)

            } else if (side == Side.RIGHT) {
                msgimageContainer.gravity = Gravity.RIGHT
                msgimageContainer.setPadding(convertDpToPixel(64f, msgimageContainer.context), 0, 0, 0)
                binding.cardColor = getRightColor(binding.msgimageCard.context)
            }
        }

        val image = binding.msgimageImage
        val fixedUrl = UrlUtils.fixUrlForEmulator(message.imageUrl)

        Glide.with(image.context).clear(image)
        Glide.with(image.context)
            .load(fixedUrl)
            .placeholder(R.drawable.avatar_placeholder)
            .error(R.drawable.avatar_failure)
            .into(image)
    }

    private fun getRightColor(context: Context): Int {
        val typedValue = TypedValue()
        val a: TypedArray = context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.messageCloudColor))
        val color = a.getColor(0, 0)
        a.recycle()
        return color
    }

    override fun getLayout(): Int {
        return R.layout.element_image_message
    }

}