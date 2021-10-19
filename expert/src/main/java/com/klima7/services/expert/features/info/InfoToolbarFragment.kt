package com.klima7.services.expert.features.info

import androidx.fragment.app.Fragment
import com.klima7.services.common.ui.toolbar.ToolbarFragment
import com.klima7.services.expert.R

class InfoToolbarFragment: ToolbarFragment(
    menu = R.menu.menu_home_toolbar,
    back = true,
) {

    override fun fragment(): Fragment = InfoFragment()

    override fun configToolbar() {
        toolbar.apply {
            title = "Zmiana profilu"
        }
    }

}