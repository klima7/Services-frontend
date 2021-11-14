package com.klima7.services.common.components.msgsend

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentSendMessageBinding
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SendMessageFragment: BaseFragment<FragmentSendMessageBinding>() {

    override val layoutId = R.layout.fragment_send_message
    override val viewModel: SendMessageViewModel by viewModel()

}