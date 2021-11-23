package com.klima7.services.client.features.offer

import android.content.Intent
import android.view.MenuItem
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentOfferBinding
import com.klima7.services.client.features.addcomm.AddCommActivity
import com.klima7.services.common.components.msgsend.SendMessageFragment
import com.klima7.services.common.components.msgviewer.MessageViewerFragment
import com.klima7.services.common.models.Role
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

class OfferFragment: BaseFragment<FragmentOfferBinding>() {

    override val layoutId = R.layout.fragment_offer
    override val viewModel: OfferViewModel by viewModel()

    override fun init() {
        val toolbar = binding.offerToolbar
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        toolbar.inflateMenu(R.menu.offer)
        toolbar.setOnMenuItemClickListener { item ->
            menuItemClicked(item)
            true
        }
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

        viewModel.start(offerId)
    }

    private fun menuItemClicked(item: MenuItem) {
        viewModel.addCommentClicked()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is OfferViewModel.Event.ShowAddCommentScreen -> showAddCommentScreen(event.offerId)
        }
    }

    private fun showAddCommentScreen(offerId: String) {
        val intent = Intent(activity, AddCommActivity::class.java)
        intent.putExtra("offerId", offerId)
        startActivity(intent)
    }
}