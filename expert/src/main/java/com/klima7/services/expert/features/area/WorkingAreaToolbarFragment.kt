package com.klima7.services.expert.features.area

import androidx.fragment.app.Fragment
import com.klima7.services.common.components.toolbar.ToolbarFragment

class WorkingAreaToolbarFragment: ToolbarFragment(
    back = true,
) {

    override fun fragment(): Fragment = WorkingAreaFragment()

    override fun configToolbar() {
        toolbar.apply {
            title = "Zmiana lokalizacji"
        }
    }

}