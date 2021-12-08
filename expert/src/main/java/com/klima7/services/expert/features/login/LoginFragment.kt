package com.klima7.services.expert.features.login

import androidx.fragment.app.Fragment
import com.klima7.services.common.components.login.BaseLoginFragment
import com.klima7.services.expert.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment: BaseLoginFragment(R.string.app_subtitle) {

    override val viewModel: LoginViewModel by viewModel()

    override fun createDecorationFragment(): Fragment {
        return DecorationFragment()
    }

}