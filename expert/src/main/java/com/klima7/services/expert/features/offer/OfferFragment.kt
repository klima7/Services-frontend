package com.klima7.services.expert.features.offer

import android.view.MenuItem
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
        toolbar.inflateMenu(R.menu.offer)
        toolbar.setOnMenuItemClickListener { item ->
            menuItemClicked(item)
            true
        }

        val callItem = toolbar.menu.findItem(R.id.offer_item_call_client)
        val showRatingItem = toolbar.menu.findItem(R.id.offer_item_show_rating)
        val archiveItem = toolbar.menu.findItem(R.id.offer_item_archive)
        val unarchiveItem = toolbar.menu.findItem(R.id.offer_item_unarchive)

        viewModel.callItemVisible.observe(viewLifecycleOwner, callItem::setVisible)
        viewModel.showRatingItemVisible.observe(viewLifecycleOwner, showRatingItem::setVisible)
        viewModel.archiveItemVisible.observe(viewLifecycleOwner, archiveItem::setVisible)
        viewModel.unarchiveItemVisible.observe(viewLifecycleOwner, unarchiveItem::setVisible)
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

        viewModel.start(offerId)
    }

    private fun menuItemClicked(item: MenuItem) {
        when(item.itemId) {
            R.id.offer_item_call_client -> viewModel.callClientClicked()
            R.id.offer_item_show_rating -> viewModel.showRatingClicked()
        }
    }

    override fun onSendMessageClicked(smb: SendMessageBarView) {
    }

    override fun onSelectImageClicked(smb: SendMessageBarView) {
    }
}