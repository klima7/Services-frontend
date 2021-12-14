package com.klima7.services.common.components.msgviewer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.models.*
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.common.platform.CombinedLiveData
import com.xwray.groupie.Group
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.util.*

class MessageViewerViewModel(
    private val getMessagesFlowUC: GetMessagesFlowUC,
    private val getConverserReadTimeUC: GetConverserReadTimeUC,
): BaseViewModel(), RatingMessageItem.Listener {

    sealed class Event: BaseEvent() {
        data class RemoveNotifications(val offerId: String): Event()
        data class ShowRating(val ratingId: String): Event()
    }

    private val messages = MutableLiveData<List<Message>>(listOf())
    private val readTime = MutableLiveData<Date?>()
    val messagesItems = CombinedLiveData(messages, readTime) {
        createItems(messages.value, readTime.value)
    }

    private lateinit var role: Role

    @ExperimentalCoroutinesApi
    fun start(offerId: String, role: Role) {
        this.role = role
        getMessages(role, offerId)
        getReadTime(role, offerId)
    }

    private fun getMessages(role: Role, offerId: String) {
        getMessagesFlowUC.start(
            viewModelScope,
            GetMessagesFlowUC.Params(role, offerId),
            { },
            { messagesFlow ->
                messagesFlow.collect {
                    sendEvent(Event.RemoveNotifications(offerId))
                    messages.value = it
                }
            }
        )
    }

    private fun getReadTime(role: Role, offerId: String) {
        getConverserReadTimeUC.start(
            viewModelScope,
            GetConverserReadTimeUC.Params(role, offerId),
            { },
            { messagesFlow ->
                messagesFlow.collect {
                    sendEvent(Event.RemoveNotifications(offerId))
                    readTime.value = it
                    Log.i("Hello", "Received read time: $it")
                }
            }
        )
    }

    private fun createItems(messages: List<Message>?, readTime: Date?): List<Group> {
        if(messages == null) {
            return listOf()
        }

        val items = mutableListOf<Group>()
        for(message in messages) {
            val side = getSide(message)
            when(message) {
                is TextMessage -> items.add(TextMessageItem(message, side))
                is ImageMessage -> items.add(ImageMessageItem(message, side))
                is StatusChangeMessage -> items.add(StatusChangeMessageItem(message, side))
                is RatingMessage -> items.add(RatingMessageItem(message, side, this))
            }
        }
        items.add(ReadByConverserItem())
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

    override fun showRatingClicked(ratingId: String) {
        sendEvent(Event.ShowRating(ratingId))
    }
}