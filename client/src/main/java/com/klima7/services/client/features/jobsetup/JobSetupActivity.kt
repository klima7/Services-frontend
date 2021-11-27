package com.klima7.services.client.features.jobsetup

import com.klima7.services.common.platform.BaseActivity

class JobSetupActivity: BaseActivity() {

    private val newJobFragment = JobSetupFragment()

    override fun fragment() = newJobFragment

    override fun onBackPressed() {
        newJobFragment.backButtonClicked()
    }
}
