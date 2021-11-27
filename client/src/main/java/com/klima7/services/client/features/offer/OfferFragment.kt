package com.klima7.services.client.features.offer

import android.content.Intent
import android.view.MenuItem
import androidx.core.os.bundleOf
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentOfferBinding
import com.klima7.services.client.features.addcomm.AddCommActivity
import com.klima7.services.client.features.profile.ProfileActivity
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

    private lateinit var rateItem: MenuItem

    override fun init() {
        val toolbar = binding.offerToolbar
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        toolbar.inflateMenu(R.menu.offer)
        toolbar.setOnMenuItemClickListener { item ->
            menuItemClicked(item)
            true
        }

        rateItem = toolbar.menu.findItem(R.id.item_add_comment)

        viewModel.rateVisible.observe(viewLifecycleOwner) { visible ->
            rateItem.isVisible = visible
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
        when(item.itemId) {
            R.id.item_add_comment -> viewModel.addCommentClicked()
            R.id.item_call_expert -> viewModel.callExpertClicked()
            R.id.item_expert_profile -> viewModel.showExpertProfileClicked()
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is OfferViewModel.Event.ShowAddCommentScreen -> showAddCommentScreen(event.offerId)
            is OfferViewModel.Event.ShowExpertProfileScreen -> showExpertProfileScreen(event.expertUid)
        }
    }

    private fun showAddCommentScreen(offerId: String) {
        val intent = Intent(activity, AddCommActivity::class.java)
        intent.putExtra("offerId", offerId)
        startActivity(intent)
    }

    private fun showExpertProfileScreen(expertUid: String) {
        val intent = Intent(requireContext(), ProfileActivity::class.java)
        val bundle = bundleOf("expertUid" to expertUid)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}