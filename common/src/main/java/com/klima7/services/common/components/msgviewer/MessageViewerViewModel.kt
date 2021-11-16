package com.klima7.services.common.components.msgviewer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.data.repositories.MessagesRepository
import com.klima7.services.common.models.Message
import com.klima7.services.common.platform.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MessageViewerViewModel(
    private val messagesRepository: MessagesRepository
): BaseViewModel() {

    val messages = MutableLiveData<List<Message>>(listOf())

    @ExperimentalCoroutinesApi
    fun start(offerId: String) {
        viewModelScope.launch {
            messagesRepository.getMessages(offerId).collect {
                messages.value = it
            }
        }
    }
}