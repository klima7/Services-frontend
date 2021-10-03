package com.klima7.services.expert.features.login

import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment: BaseFragment<FragmentLoginBinding>() {

    override val layoutId = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModel()

}