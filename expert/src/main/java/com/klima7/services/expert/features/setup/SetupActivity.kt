package com.klima7.services.expert.features.setup

import com.klima7.services.common.lib.base.BaseActivity
import com.klima7.services.common.lib.failurable.FailurableWrapperFragment

class SetupActivity: BaseActivity() {
    override fun fragment() = FailurableWrapperFragment(SetupFragment())
}