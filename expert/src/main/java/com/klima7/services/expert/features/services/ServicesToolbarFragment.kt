package com.klima7.services.expert.features.services

import androidx.fragment.app.Fragment
import com.klima7.services.common.ui.toolbar.ToolbarFragment

class ServicesToolbarFragment: ToolbarFragment(
    back = true,
) {

    override fun fragment(): Fragment = ServicesFragment()

    override fun configToolbar() {
        toolbar.apply {
            title = "Zmiana usług"
        }
    }

}