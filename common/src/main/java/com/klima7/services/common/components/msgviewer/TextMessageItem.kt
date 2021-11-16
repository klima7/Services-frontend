package com.klima7.services.common.components.msgviewer

import android.view.View
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ElementTextMessageBinding
import com.klima7.services.common.models.TextMessage
import com.xwray.groupie.databinding.BindableItem


class TextMessageItem(
    private val message: TextMessage,
    ) : BindableItem<ElementTextMessageBinding>() {

    override fun bind(binding: ElementTextMessageBinding, position: Int) {
        binding.msgtextText.text = message.text;
        binding.msgtextPending.visibility = if(message.pending) View.VISIBLE else View.GONE
    }

    override fun getLayout(): Int {
        return R.layout.element_text_message
    }

}