package com.klima7.services.common.components.msgviewer

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentMessageViewerBinding
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
                        val messageItem = TextMessageItem(message, "http://localhost:9199/v0/b/services-a7389.appspot.com/o/profile_images%2FGVUPHpMgt36NtVg9vsClFSaaOQQ7.png?alt=media&token=818d7026-6b4c-47bb-a859-ac1a47a8a3b4")
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