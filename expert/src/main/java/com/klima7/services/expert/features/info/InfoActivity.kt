package com.klima7.services.expert.features.info

import com.klima7.services.common.lib.base.BaseActivity
import com.klima7.services.common.lib.failurable.FailurableWrapperFragment

class InfoActivity: BaseActivity() {
    override fun fragment() = InfoFragment()
}