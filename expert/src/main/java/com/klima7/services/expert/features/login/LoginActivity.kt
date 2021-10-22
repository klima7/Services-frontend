package com.klima7.services.expert.features.login

import com.klima7.services.common.platform.BaseActivity

class LoginActivity: BaseActivity() {
    override fun fragment() = LoginFragment()

    override fun onBackPressed() {}
}