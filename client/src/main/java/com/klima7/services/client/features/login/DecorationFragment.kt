package com.klima7.services.client.features.login

import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentLoginDecorationBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel

class DecorationFragment: BaseFragment<FragmentLoginDecorationBinding>() {

    override val layoutId = R.layout.fragment_login_decoration
    override val viewModel = BaseViewModel()

}