package com.klima7.services.expert.features.offer

import com.klima7.services.common.components.msgsend.SendMessageFragment
import com.klima7.services.common.components.msgviewer.MessageViewerFragment
import com.klima7.services.common.components.offer.BaseOfferFragment
import com.klima7.services.common.components.views.SendMessageBarView
import com.klima7.services.common.models.Role
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentOfferBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

class OfferFragment: BaseOfferFragment<FragmentOfferBinding>(), SendMessageBarView.Listener {

    override val layoutId = R.layout.fragment_offer
    override val viewModel: OfferViewModel by viewModel()

    override fun init() {
        super.init()
        val toolbar = binding.offerToolbar
        toolbar.setNavigationIcon(R.drawable.icon_arrow_back)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    @ExperimentalCoroutinesApi
    override fun onFirstCreation() {
        super.onFirstCreation()

        val offerId = arguments?.getString("offerId") ?: throw Error("offerId not supplied")

        val messageViewerFragment = MessageViewerFragment.newInstance(offerId, Role.EXPERT)
        val sendMessageFragment = SendMessageFragment.newInstance(offerId, Role.EXPERT)

        childFragmentManager
            .beginTransaction()
            .add(R.id.offer_chat_container, messageViewerFragment)
            .add(R.id.offer_msgsend_container, sendMessageFragment)
            .commit()
    }

    override fun onSendMessageClicked(smb: SendMessageBarView) {
    }

    override fun onSelectImageClicked(smb: SendMessageBarView) {
    }
}