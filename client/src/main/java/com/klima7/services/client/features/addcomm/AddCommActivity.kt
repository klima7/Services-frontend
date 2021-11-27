package com.klima7.services.client.features.addcomm

import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.klima7.services.common.platform.BaseActivity

class AddCommActivity: BaseActivity() {
    override fun fragment() = AddCommFragment()

    override fun overrideExitTransition() {
        super.overrideExitTransition()
        Animatoo.animateSlideDown(this)
    }
}