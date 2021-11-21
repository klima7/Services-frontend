package com.klima7.services.client.features.login

import androidx.fragment.app.Fragment
import com.klima7.services.client.R
import com.klima7.services.common.components.login.BaseLoginFragment

class LoginFragment: BaseLoginFragment(R.string.app_subtitle) {

    override fun createDecorationFragment(): Fragment {
        return DecorationFragment()
    }

}