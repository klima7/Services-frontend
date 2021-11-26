package com.klima7.services.common.components.splash

import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.klima7.services.common.platform.BaseActivity

abstract class BaseSplashActivity: BaseActivity() {

    override fun overrideStartTransition() {
        super.overrideStartTransition()
        Animatoo.animateDiagonal(this)
    }
}
