package com.klima7.services.expert.features.splash

import com.klima7.services.common.lib.base.BaseActivity
import com.klima7.services.common.lib.loadable.LoadableWrapperFragment

class SplashActivity: BaseActivity() {
    override fun fragment() = LoadableWrapperFragment(SplashFragment())
}