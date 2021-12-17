package com.klima7.services.common.components.msgviewer

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.klima7.services.common.R
import com.klima7.services.common.components.rating.RatingActivity
import com.klima7.services.common.databinding.FragmentMessageViewerBinding
import com.klima7.services.common.models.Role
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.xwray.groupie.Group
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.groupiex.plusAssign
import org.koin.androidx.viewmodel.ext.android.viewModel


class MessageViewerFragment:
    BaseFragment<FragmentMessageViewerBinding>(), View.OnLayoutChangeListener{

    override val layoutId = R.layout.fragment_message_viewer
    override val viewModel: MessageViewerViewModel by viewModel()

    private val adapter = GroupieAdapter()
    private val messagesSection = Section()

    private lateinit var recycler: RecyclerView
    private lateinit var scrollButton: FloatingActionButton

    override fun init() {
        super.init()

        recycler = binding.msgviewerRecycler
        scrollButton = binding.msgviewerScrollButton

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        adapter += messagesSection

        viewModel.messagesItems.observe(viewLifecycleOwner) { newMessages ->
            updateMessages(newMessages)
        }

        recycler.addOnLayoutChangeListener(this)

        scrollButton.setOnClickListener {
            binding.msgviewerRecycler.smoothScrollToPosition(adapter.itemCount - 1)
        }

        binding.msgviewerRecycler.addOnScrollListener(ScrollListener())
    }

    override fun onFirstCreation() {
        super.onFirstCreation()

        val offerId = arguments?.getString("offerId") ?: throw Error("offerId not supplied")
        val role = arguments?.getSerializable("role") as? Role  ?: throw Error("role not supplied")

        viewModel.start(offerId, role)
    }

    private fun updateMessages(newMessages: List<Group>) {
        val newMessagesSection = Section()
        val isBottom = isOnBottom()
        newMessagesSection.addAll(newMessages)
        messagesSection.replaceAll(listOf(newMessagesSection))
        if (isBottom) {
            binding.msgviewerRecycler.scrollToPosition(adapter.itemCount - 1)
        }
    }

    override fun onLayoutChange(
        v: View?,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        oldLeft: Int,
        oldTop: Int,
        oldRight: Int,
        oldBottom: Int
    ) {
        val oldHeight = oldBottom - oldTop
        val newHeight = bottom - top

        if(newHeight < oldHeight) {
            Handler(Looper.getMainLooper()).postDelayed(
                { binding.msgviewerRecycler.scrollToPosition(adapter.itemCount - 1) },
                0
            )
        }
    }

    private fun isOnBottom(): Boolean {
        return !recycler.canScrollVertically(1)
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is MessageViewerViewModel.Event.RemoveNotifications -> removeNotifications(event.offerId)
            is MessageViewerViewModel.Event.ShowRating -> showRating(event.ratingId)
        }
    }

    private fun removeNotifications(offerId: String) {
        NotificationManagerCompat.from(requireActivity()).cancel(offerId.hashCode())
    }

    private fun showRating(ratingId: String) {
        val intent = Intent(requireContext(), RatingActivity::class.java)
        val bundle = bundleOf(
            "ratingId" to ratingId,
            "exit" to "slideDown",
        )
        intent.putExtras(bundle)
        startActivity(intent)
        Animatoo.animateSlideUp(requireActivity())
    }

    private inner class ScrollListener: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if(isOnBottom()) {
                scrollButton.hide()
            }
            else {
                scrollButton.show()
            }
        }
    }

    companion object {
        fun newInstance(offerId: String, role: Role) =
            MessageViewerFragment().apply {
                arguments = bundleOf(
                    "offerId" to offerId,
                    "role" to role
                )
            }
    }

    override fun onStart() {
        super.onStart()
        viewModel.screenStarted()
    }

    override fun onStop() {
        super.onStop()
        viewModel.screenStopped()
    }
}