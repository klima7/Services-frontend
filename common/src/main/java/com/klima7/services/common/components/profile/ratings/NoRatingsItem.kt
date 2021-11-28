package com.klima7.services.common.components.profile.ratings

import com.klima7.services.common.R
import com.klima7.services.common.databinding.ElementCreditsHeadingBinding
import com.klima7.services.common.databinding.ElementNoRatingsBinding
import com.xwray.groupie.databinding.BindableItem


class NoRatingsItem : BindableItem<ElementNoRatingsBinding>() {

    override fun bind(binding: ElementNoRatingsBinding, position: Int) {}

    override fun getLayout() = R.layout.element_no_ratings

}