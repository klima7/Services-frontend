package com.klima7.services.expert.features.info

import androidx.fragment.app.Fragment
import com.klima7.services.common.components.toolbar.ToolbarFragment

class InfoToolbarFragment: ToolbarFragment(
    back = true,
) {

    override fun fragment(): Fragment = InfoFragment()

    override fun configToolbar() {
        toolbar.apply {
            title = "Zmiana profilu"
        }
    }

}