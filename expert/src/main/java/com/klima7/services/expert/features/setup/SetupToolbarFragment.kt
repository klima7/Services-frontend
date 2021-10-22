package com.klima7.services.expert.features.setup

import androidx.fragment.app.Fragment
import com.klima7.services.common.components.toolbar.ToolbarFragment

class SetupToolbarFragment: ToolbarFragment() {

    override fun fragment(): Fragment = SetupFragment()

    override fun configToolbar() {
        toolbar.apply {
            title = "Uzupełnianie danych"
        }
    }

}