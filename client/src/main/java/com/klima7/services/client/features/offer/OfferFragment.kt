package com.klima7.services.client.features.offer

import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentOfferBinding
import com.klima7.services.client.features.addrating.AddRatingActivity
import com.klima7.services.client.features.profile.ProfileActivity
import com.klima7.services.common.components.rating.RatingActivity
import com.klima7.services.common.components.msgsend.SendMessageFragment
import com.klima7.services.common.components.msgviewer.MessageViewerFragment
import com.klima7.services.common.components.offer.BaseOfferFragment
import com.klima7.services.common.models.Role
import com.klima7.services.common.platform.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class OfferFragment: BaseOfferFragment<FragmentOfferBinding>() {

    override val layoutId = R.layout.fragment_offer
    override val viewModel: OfferViewModel by viewModel()

    private lateinit var rateItem: MenuItem
    private lateinit var callItem: MenuItem
    private lateinit var showRatingItem: MenuItem

    override fun init() {
        super.init()
        val toolbar = binding.offerToolbar
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        toolbar.inflateMenu(R.menu.offer)
        toolbar.setOnMenuItemClickListener { item ->
            menuItemClicked(item)
            true
        }

        rateItem = toolbar.menu.findItem(R.id.item_add_comment)
        showRatingItem = toolbar.menu.findItem(R.id.item_show_comment)
        callItem = toolbar.menu.findItem(R.id.item_call_expert)

        viewModel.addRatingItemVisible.observe(viewLifecycleOwner) { visible ->
            rateItem.isVisible = visible
        }

        viewModel.showRatingItemVisible.observe(viewLifecycleOwner) { visible ->
            showRatingItem.isVisible = visible
        }

        viewModel.callItemVisible.observe(viewLifecycleOwner) { visible ->
            callItem.isVisible = visible
        }

        lifecycleScope.launch {
            binding.offerStatusToolbar.setVisibleInstant(false)
            delay(300)
            binding.offerStatusToolbar.setVisibleAnimate(true)
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
            R.id.item_show_comment -> viewModel.showCommentClicked()
            R.id.item_call_expert -> viewModel.callExpertClicked()
            R.id.item_expert_profile -> viewModel.showExpertProfileClicked()
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is OfferViewModel.Event.ShowAddCommentScreen -> showAddCommentScreen(event.offerId)
            is OfferViewModel.Event.ShowCommentScreen -> showCommentScreen(event.ratingId)
            is OfferViewModel.Event.ShowExpertProfileScreen -> showExpertProfileScreen(event.expertUid)
            is OfferViewModel.Event.Call -> call(event.phoneNumber)
        }
    }

    private fun showAddCommentScreen(offerId: String) {
        val intent = Intent(activity, AddRatingActivity::class.java)
        val bundle = bundleOf(
            "offerId" to offerId,
            "exit" to "slideDown",
        )
        intent.putExtras(bundle)
        startActivity(intent)
        Animatoo.animateSlideUp(requireActivity())
    }

    private fun showCommentScreen(ratingId: String) {
        val intent = Intent(requireContext(), RatingActivity::class.java)
        val bundle = bundleOf(
            "ratingId" to ratingId,
            "exit" to "slideDown",
        )
        intent.putExtras(bundle)
        startActivity(intent)
        Animatoo.animateSlideUp(requireActivity())
    }

    private fun showExpertProfileScreen(expertUid: String) {
        val intent = Intent(requireContext(), ProfileActivity::class.java)
        val bundle = bundleOf(
            "expertUid" to expertUid,
            "exit" to "slideDown",
        )
        intent.putExtras(bundle)
        startActivity(intent)
        Animatoo.animateSlideUp(requireActivity())
    }

    private fun call(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:$phoneNumber"))
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            binding.offerStatusToolbar.setVisibleInstant(false)
            delay(300)
            binding.offerStatusToolbar.setVisibleAnimate(true)
        }
    }
}