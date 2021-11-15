package com.klima7.services.common.components.msgsend

import android.util.Log
import com.klima7.services.common.R
import com.klima7.services.common.components.views.SendMessageBarView
import com.klima7.services.common.databinding.FragmentSendMessageBinding
import com.klima7.services.common.models.MessageSender
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SendMessageFragment: BaseFragment<FragmentSendMessageBinding>(), SendMessageBarView.Listener {

    override val layoutId = R.layout.fragment_send_message
    override val viewModel: SendMessageViewModel by viewModel()

    override fun init() {
        super.init()
        binding.msgsendSendMessageBar.setListener(this)
    }

    fun initialize(sender: MessageSender, offerId: String) {
        setSender(sender)
        setOffer(offerId)
    }

    fun setSender(sender: MessageSender) {
        viewModel.setSender(sender)
    }

    fun setOffer(offerId: String) {
        viewModel.setOffer(offerId)
    }

    override fun onSendMessageClicked(smb: SendMessageBarView) {
        Log.i("Hello", "Send message")
        viewModel.sendMessage(smb.text)
        smb.clear()
    }

    override fun onSelectImageClicked(smb: SendMessageBarView) {
        Log.i("Hello", "Select image")
    }
}