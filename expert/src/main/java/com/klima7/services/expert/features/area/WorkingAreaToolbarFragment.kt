package com.klima7.services.expert.features.area

import androidx.fragment.app.Fragment
import com.klima7.services.common.ui.toolbar.ToolbarFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.features.info.InfoFragment

class WorkingAreaToolbarFragment: ToolbarFragment(
    menu = R.menu.menu_home_toolbar,
    back = true,
) {

    override fun fragment(): Fragment = WorkingAreaFragment()

    override fun configToolbar() {
        toolbar.apply {
            title = "Zmiana profilu"
        }
    }

}