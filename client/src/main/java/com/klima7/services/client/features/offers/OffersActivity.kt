package com.klima7.services.client.features.offers

import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.klima7.services.common.platform.BaseActivity

class OffersActivity: BaseActivity() {
    override fun fragment() = OffersFragment()

    override fun overrideStartTransition() {
        super.overrideStartTransition()
        Animatoo.animateSlideRight(this)
    }

    override fun overrideExitTransition() {
        super.overrideExitTransition()
        Animatoo.animateSlideLeft(this)
    }
}