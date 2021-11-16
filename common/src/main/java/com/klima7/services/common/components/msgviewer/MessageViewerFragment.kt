package com.klima7.services.common.components.msgviewer

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentMessageViewerBinding
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessageViewerFragment: BaseFragment<FragmentMessageViewerBinding>() {

    override val layoutId = R.layout.fragment_message_viewer
    override val viewModel: MessageViewerViewModel by viewModel()

}