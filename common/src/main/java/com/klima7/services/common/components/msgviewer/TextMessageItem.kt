package com.klima7.services.common.components.msgviewer

import com.klima7.services.common.R
import com.klima7.services.common.databinding.ElementTestBinding
import com.klima7.services.common.models.ProfileImage
import com.klima7.services.common.models.TextMessage
import com.xwray.groupie.databinding.BindableItem


class TextMessageItem(
    private val message: TextMessage,
    private val profileImageUrl: String,
    ) : BindableItem<ElementTestBinding>() {

    override fun bind(binding: ElementTestBinding, position: Int) {
        binding.msgtextAvatar.setProfileImage(ProfileImage(profileImageUrl))
        binding.msgtextText.text = message.text;
    }

    override fun getLayout(): Int {
        return R.layout.element_test
    }

}