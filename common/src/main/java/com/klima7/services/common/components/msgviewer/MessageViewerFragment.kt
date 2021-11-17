package com.klima7.services.common.components.msgviewer

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentMessageViewerBinding
import com.klima7.services.common.models.ImageMessage
import com.klima7.services.common.models.Message
import com.klima7.services.common.models.Role
import com.klima7.services.common.models.TextMessage
import com.klima7.services.common.platform.BaseFragment
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.groupiex.plusAssign
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
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
        binding.msgviewerRecycler.layoutManager = LinearLayoutManager(requireContext())

        adapter += messagesSection

        viewModel.messages.observe(viewLifecycleOwner) { newMessages ->
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

        viewModel.start(offerId)
    }

    private fun updateMessages(newMessages: List<Message>) {
        val newMessagesSection = Section()
        val isBottom = isOnBottom()
        for (message in newMessages) {
            when (message) {
                is TextMessage -> {
                    val messageItem = TextMessageItem(message, Side.RIGHT)
                    newMessagesSection.add(messageItem)
                }
                is ImageMessage -> {
                    val messageItem = ImageMessageItem(message, Side.RIGHT)
                    newMessagesSection.add(messageItem)
                }
            }
        }
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
                Runnable { binding.msgviewerRecycler.scrollToPosition(adapter.itemCount - 1) },
                0
            )
        }
    }

    private fun isOnBottom(): Boolean {
        return !recycler.canScrollVertically(1)
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

}