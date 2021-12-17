package com.klima7.services.common.components.msgviewer

import android.content.Context
import android.content.res.TypedArray
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ElementTextMessageBinding
import com.klima7.services.common.models.TextMessage
import com.klima7.services.common.ui.convertDpToPixel
import com.xwray.groupie.databinding.BindableItem


class TextMessageItem(
    private val message: TextMessage,
    private val side: Side,
    ) : BindableItem<ElementTextMessageBinding>() {

    override fun bind(binding: ElementTextMessageBinding, position: Int) {
        binding.apply {
            msgtextText.text = message.text
            msgtextPending.visibility = if(message.pending) View.VISIBLE else View.GONE

            if(side == Side.LEFT) {
                msgtextContainer.gravity = Gravity.LEFT
                msgtextContainer.setPadding(0, 0, convertDpToPixel(64f, msgtextContainer.context), 0)
                binding.cardColor = binding.msgtextCard.context.resources.getColor(R.color.left_message_cloud_color, null)
            }
            else if(side == Side.RIGHT) {
                msgtextContainer.gravity = Gravity.RIGHT
                msgtextContainer.setPadding(convertDpToPixel(64f, msgtextContainer.context), 0, 0, 0)
                binding.cardColor = getRightColor(binding.msgtextCard.context)
            }
        }
    }

    private fun getRightColor(context: Context): Int {
        val typedValue = TypedValue()
        val a: TypedArray = context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.messageCloudColor))
        val color = a.getColor(0, 0)
        a.recycle()
        return color
    }

    override fun getLayout(): Int {
        return R.layout.element_text_message
    }

}