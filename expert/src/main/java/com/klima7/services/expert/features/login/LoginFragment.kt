package com.klima7.services.expert.features.login

import androidx.fragment.app.Fragment
import com.klima7.services.common.components.login.BaseLoginFragment
import com.klima7.services.expert.R

class LoginFragment: BaseLoginFragment(R.string.app_subtitle) {

    override fun createDecorationFragment(): Fragment {
        return DecorationFragment()
    }

}