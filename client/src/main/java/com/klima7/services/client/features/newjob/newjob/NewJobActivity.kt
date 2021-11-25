package com.klima7.services.client.features.newjob.newjob

import com.klima7.services.common.platform.BaseActivity

class NewJobActivity: BaseActivity() {

    private val newJobFragment = NewJobFragment()

    override fun fragment() = newJobFragment

    override fun onBackPressed() {
        newJobFragment.backButtonClicked()
    }
}