package com.klima7.services.expert.features.services

import androidx.fragment.app.Fragment
import com.klima7.services.common.ui.toolbar.ToolbarFragment
import com.klima7.services.expert.R

class ServicesToolbarFragment: ToolbarFragment(
    menu = R.menu.menu_home_toolbar,
    back = true,
) {

    override fun fragment(): Fragment = ServicesFragment()

    override fun configToolbar() {
        toolbar.apply {
            title = "Zmiana usług"
        }
    }

}