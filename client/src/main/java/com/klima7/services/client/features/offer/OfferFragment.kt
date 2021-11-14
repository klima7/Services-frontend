package com.klima7.services.client.features.offer

import android.util.Log
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentOfferBinding
import com.klima7.services.common.components.views.SendMessageBarView
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class OfferFragment: BaseFragment<FragmentOfferBinding>(), SendMessageBarView.Listener {

    override val layoutId = R.layout.fragment_offer
    override val viewModel: OfferViewModel by viewModel()

    override fun init() {
        val toolbar = binding.offerToolbar
        toolbar.setNavigationIcon(R.drawable.icon_arrow_back)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        binding.offerSendMessageBar.setListener(this)
    }

    override fun onSendMessageClicked(smb: SendMessageBarView) {
        Log.i("Hello", "Send message clicked")
    }

    override fun onSelectImageClicked(smb: SendMessageBarView) {
        Log.i("Hello", "Select image clicked")
    }
}