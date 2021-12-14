package com.klima7.services.common.components.msgviewer

import com.klima7.services.common.R
import com.klima7.services.common.databinding.ElementReadByConverserBinding
import com.xwray.groupie.databinding.BindableItem


class ReadByConverserItem : BindableItem<ElementReadByConverserBinding>() {

    override fun bind(binding: ElementReadByConverserBinding, position: Int) { }

    override fun getLayout(): Int {
        return R.layout.element_read_by_converser
    }

}