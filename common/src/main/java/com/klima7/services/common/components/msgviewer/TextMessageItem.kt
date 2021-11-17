package com.klima7.services.common.components.msgviewer

import android.view.Gravity
import android.view.View
import androidx.core.view.marginLeft
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ElementTextMessageBinding
import com.klima7.services.common.models.TextMessage
import com.klima7.services.common.ui.convertDpToPixel
import com.xwray.groupie.databinding.BindableItem


class TextMessageItem(
    private val message: TextMessage,
    private val color: Int,
    private val side: Side,
    ) : BindableItem<ElementTextMessageBinding>() {

    override fun bind(binding: ElementTextMessageBinding, position: Int) {
        binding.apply {
            msgtextText.text = message.text;
            msgtextPending.visibility = if(message.pending) View.VISIBLE else View.GONE
            msgtextCard.setCardBackgroundColor(color)

            if(side == Side.LEFT) {
                msgtextContainer.gravity = Gravity.LEFT
                msgtextContainer.setPadding(0, 0, convertDpToPixel(64f, msgtextContainer.context), 0)
            }
            else if(side == Side.RIGHT) {
                msgtextContainer.gravity = Gravity.RIGHT
                msgtextContainer.setPadding(convertDpToPixel(64f, msgtextContainer.context), 0, 0, 0)
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.element_text_message
    }

}