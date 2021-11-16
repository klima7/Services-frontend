package com.klima7.services.common.components.msgviewer

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentMessageViewerBinding
import com.klima7.services.common.models.ImageMessage
import com.klima7.services.common.models.TextMessage
import com.klima7.services.common.platform.BaseFragment
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.groupiex.plusAssign
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessageViewerFragment: BaseFragment<FragmentMessageViewerBinding>() {

    override val layoutId = R.layout.fragment_message_viewer
    override val viewModel: MessageViewerViewModel by viewModel()

    @ExperimentalCoroutinesApi
    override fun init() {
        super.init()

        val adapter = GroupieAdapter()
        binding.msgviewerRecycler.adapter = adapter
        binding.msgviewerRecycler.layoutManager = LinearLayoutManager(requireContext())

        val messages = Section()
        adapter += messages

        viewModel.messages.observe(viewLifecycleOwner) { newMessages ->
            val group = Section()
            for(message in newMessages) {
                when(message) {
                    is TextMessage -> {
                        val messageItem = TextMessageItem(message)
                        group.add(messageItem)
                    }
                    is ImageMessage -> {
                        val messageItem = ImageMessageItem(message)
                        group.add(messageItem)
                    }
                }
            }
            messages.replaceAll(listOf(group))
        }

        viewModel.start("offer10")

//        for(i in 0..15) {
//            messages += TextMessageItem()
//        }
//
//        lifecycleScope.launch {
//            val newMessages = Section()
//            delay(4000)
//            for(i in 0..1000) {
//                newMessages += TextMessageItem()
//            }
//            adapter.replaceAll(listOf(newMessages))
//        }
    }
}