package com.klima7.services.client.features.offer

import android.util.Log
import android.widget.ArrayAdapter
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentOfferBinding
import com.klima7.services.common.components.msgsend.SendMessageFragment
import com.klima7.services.common.components.msgviewer.MessageViewerFragment
import com.klima7.services.common.components.views.SendMessageBarView
import com.klima7.services.common.models.Role
import com.klima7.services.common.platform.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

class OfferFragment: BaseFragment<FragmentOfferBinding>(), SendMessageBarView.Listener {

    override val layoutId = R.layout.fragment_offer
    override val viewModel: OfferViewModel by viewModel()

    override fun init() {
        val toolbar = binding.offerToolbar
        toolbar.setNavigationIcon(R.drawable.icon_arrow_back)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        val list = listOf("Jan Kowalski", "Piotr Duda", "Julia Marciniak", "Juliusz Słowacka")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.offerExpertsSpinner.adapter = adapter
    }

    @ExperimentalCoroutinesApi
    override fun onFirstCreation() {
        super.onFirstCreation()

        val offerId = arguments?.getString("offerId") ?: throw Error("offerId not supplied")

        val messageViewerFragment = MessageViewerFragment.newInstance(offerId, Role.CLIENT)
        val sendMessageFragment = SendMessageFragment.newInstance(offerId, Role.CLIENT)

        childFragmentManager
            .beginTransaction()
            .add(R.id.offer_chat_container, messageViewerFragment)
            .add(R.id.offer_msgsend_container, sendMessageFragment)
            .commit()
    }

    override fun onSendMessageClicked(smb: SendMessageBarView) {
        Log.i("Hello", "Send message clicked")
    }

    override fun onSelectImageClicked(smb: SendMessageBarView) {
        Log.i("Hello", "Select image clicked")
    }
}