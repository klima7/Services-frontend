package com.klima7.services.expert.features.splash

import com.klima7.services.common.lib.base.BaseActivity
import com.klima7.services.common.lib.failfrag.FailurableWrapperFragment

class SplashActivity: BaseActivity() {
    override fun fragment() = FailurableWrapperFragment(SplashFragment())
}