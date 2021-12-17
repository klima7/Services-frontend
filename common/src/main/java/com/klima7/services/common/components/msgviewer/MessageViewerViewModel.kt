package com.klima7.services.common.components.msgviewer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.models.*
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.common.platform.CombinedLiveData
import com.xwray.groupie.Group
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class MessageViewerViewModel(
    private val getMessagesFlowUC: GetMessagesFlowUC,
    private val getConverserReadTimeUC: GetConverserReadTimeUC,
): BaseViewModel(), RatingMessageItem.Listener {

    sealed class Event: BaseEvent() {
        data class RemoveNotifications(val offerId: String): Event()
        data class ShowRating(val ratingId: String): Event()
    }

    private var offerId: String? = null
    private val messages = MutableLiveData<List<Message>>(listOf())
    private val readTime = MutableLiveData<Date?>()
    val messagesItems = CombinedLiveData(messages, readTime) {
        createItems(messages.value, readTime.value)
    }

    private lateinit var role: Role
    private var job: Job? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    fun start(offerId: String, role: Role) {
        this.offerId = offerId
        this.role = role
        getReadTime(role, offerId)
    }

    fun screenStarted() {
        offerId?.apply {
            job = launchJob(role, this)
        }
    }

    fun screenStopped() {
        job?.cancel()
    }

    private fun launchJob(role: Role, offerId: String): Job {
        return viewModelScope.launch {
            getMessagesFlowUC.run(
                GetMessagesFlowUC.Params(role, offerId)
            ).foldS(
                {},
                { messagesFlow ->
                    messagesFlow.collect {
                        sendEvent(Event.RemoveNotifications(offerId))
                        messages.value = it
                    }
                    messagesFlow.cancellable()
                })
        }
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
                }
            }
        )
    }

    private fun createItems(messages: List<Message>?, readTime: Date?): List<Group> {
        if(messages == null) {
            return listOf()
        }

        val items = mutableListOf<Group>()

        for(index: Int in messages.indices) {
            val message = messages[index]
            val nextMessage = if(index+1<messages.size) messages[index+1] else null
            val item = createItemsPart(message, nextMessage, readTime)
            items.addAll(item)
        }

        return items
    }

    private fun createItemsPart(message: Message, nextMessage: Message?, readTime: Date?): List<Group> {
        val result = mutableListOf<Group>()
        val side = getSide(message)

        when(message) {
            is TextMessage -> result.add(TextMessageItem(message, side))
            is ImageMessage -> result.add(ImageMessageItem(message, side))
            is StatusChangeMessage -> result.add(StatusChangeMessageItem(message, side))
            is RatingMessage -> result.add(RatingMessageItem(message, side, this))
        }

        if(readTime != null &&
            ((nextMessage != null && message.sendTime <= readTime && nextMessage.sendTime > readTime) ||
            (nextMessage == null && readTime >= message.sendTime))
        ) {
            result.add(ReadByConverserItem())
        }

        return result
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