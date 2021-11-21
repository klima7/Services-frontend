package com.klima7.services.common.components.settings

import com.klima7.services.common.R
import com.klima7.services.common.databinding.ElementCommentBinding
import com.klima7.services.common.databinding.ElementCreditBinding
import com.klima7.services.common.databinding.ElementCreditsHeadingBinding
import com.klima7.services.common.databinding.ElementSettingsOptionBinding
import com.xwray.groupie.databinding.BindableItem


class SettingsOptionItem(
    private val settingsOption: SettingsOption,
    private val clickListener: OnClickListener,
) : BindableItem<ElementSettingsOptionBinding>() {

    override fun bind(binding: ElementSettingsOptionBinding, position: Int) {
        val context = binding.settingselemCard.context
        binding.apply {
            settingselemIcon.setImageResource(settingsOption.iconRes)
            settingselemTitle.text = context.resources.getString(settingsOption.textRes)
            settingselemCard.setOnClickListener {
                clickListener.settingsOptionClicked(settingsOption)
            }
        }
    }

    override fun getLayout() = R.layout.element_settings_option

    interface OnClickListener {
        fun settingsOptionClicked(settingsOption: SettingsOption)
    }

}