package com.klima7.services.common.components.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingMethod
import androidx.databinding.DataBindingUtil
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ViewLastMessageBinding
import com.klima7.services.common.extensions.getPrettyTime
import com.klima7.services.common.models.*
import com.klima7.services.common.ui.OfferStatusDescription
import java.text.SimpleDateFormat
import java.util.*


@androidx.databinding.BindingMethods(
    value = [
        BindingMethod(
            type = LastMessageView::class,
            attribute = "lastMessage_message",
            method = "setMessage"
        ),
        BindingMethod(
            type = LastMessageView::class,
            attribute = "lastMessage_role",
            method = "setRole"
        ),
        BindingMethod(
            type = LastMessageView::class,
            attribute = "lastMessage_read_time",
            method = "setReadTime"
        ),
    ]
)
class LastMessageViewBindingMethods


class LastMessageView(context: Context, attrs: AttributeSet?): FrameLayout(context, attrs) {

    companion object {
        private val FORMAT = SimpleDateFormat("dd.MM.yyy HH:mm", Locale.US)
    }

    private var binding: ViewLastMessageBinding
    private var lastMessage: Message? = null
    private var readTime: Date? = null
    private var role: Role? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_last_message, this, true)
        refreshView()
    }

    fun setMessage(lastMessage: Message?) {
        this.lastMessage = lastMessage
        refreshView()
    }

    fun setReadTime(readTime: Date?) {
        this.readTime = readTime
        refreshView()
    }

    fun setRole(role: Role) {
        this.role = role
        refreshView()
    }

    private fun refreshView() {
        val cLastMessage = lastMessage
        val cReadTime = readTime

        refreshStatus(cLastMessage, cReadTime)

        binding.present = cLastMessage != null
        if(cLastMessage == null) {
            return
        }

        binding.author = getAuthorText(cLastMessage.author)
        when(cLastMessage) {
            is TextMessage -> {
                binding.message = cLastMessage.text
            }
            is ImageMessage -> {
                binding.message = context.resources.getString(R.string.view_last_message__photo_send)
            }
            is StatusChangeMessage -> {
                val statusName = OfferStatusDescription.get(cLastMessage.newStatus).getText(context)
                binding.message = context.resources.getString(R.string.view_last_message__status_changed, statusName)
            }
            is RatingMessage -> {
                binding.message = context.resources.getString(R.string.view_last_message__rating_added)
            }
        }

        binding.time = cLastMessage.sendTime.getPrettyTime()
    }

    private fun refreshStatus(message: Message?, readTime: Date?) {
        val isNeverRead = readTime == null
        val unreadMessages = if(message == null) {
            false
        } else {
            readTime?.before(message.sendTime) ?: true
        }

        if(isNeverRead && !unreadMessages) {
            binding.newOffer = true
            binding.unread = false
        }
        else if(unreadMessages) {
            binding.newOffer = false
            binding.unread = true
        }
        else {
            binding.unread = false
            binding.newOffer = false
        }
    }

    private fun getAuthorText(author: MessageAuthor): String {
        val resource = if(role?.toMessageAuthor() == author) {
            R.string.you
        } else {
            when(author) {
                MessageAuthor.EXPERT -> R.string.expert
                MessageAuthor.CLIENT -> R.string.client
            }
        }
        return context.getString(resource)
    }

}
