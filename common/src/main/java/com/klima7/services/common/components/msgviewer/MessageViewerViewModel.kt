package com.klima7.services.common.components.msgviewer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.data.repositories.MessagesRepository
import com.klima7.services.common.models.*
import com.klima7.services.common.platform.BaseViewModel
import com.xwray.groupie.Group
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MessageViewerViewModel(
    private val messagesRepository: MessagesRepository
): BaseViewModel() {

    private val messages = MutableLiveData<List<Message>>(listOf())
    val messagesItems = messages.map { messages -> createItems(messages) }

    private lateinit var role: Role

    @ExperimentalCoroutinesApi
    fun start(offerId: String, role: Role) {
        this.role = role
        viewModelScope.launch {
            messagesRepository.getMessages(offerId).collect {
                messages.value = it
            }
        }
    }

    private fun createItems(messages: List<Message>): List<Group> {
        val items = mutableListOf<Group>()
        for(message in messages) {
            val side = getSide(message)
            when(message) {
                is TextMessage -> items.add(TextMessageItem(message, side))
                is ImageMessage -> items.add(ImageMessageItem(message, side))
            }
        }
        return items
    }

    private fun getSide(message: Message): Side {
        return if(role == Role.EXPERT) {
            when(message.author) {
                MessageAuthor.EXPERT -> Side.RIGHT
                MessageAuthor.CLIENT -> Side.LEFT
            }
        }
        else {
            when(message.author) {
                MessageAuthor.EXPERT -> Side.LEFT
                MessageAuthor.CLIENT -> Side.RIGHT
            }
        }
    }
}