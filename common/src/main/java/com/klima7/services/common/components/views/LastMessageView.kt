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
    ]
)
class LastMessageViewBindingMethods


class LastMessageView(context: Context, attrs: AttributeSet?): FrameLayout(context, attrs) {

    companion object {
        private val FORMAT = SimpleDateFormat("dd.MM.yyy HH:mm", Locale.US)
    }

    private var binding: ViewLastMessageBinding
    private var lastMessage: Message? = null
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

    fun setRole(role: Role) {
        this.role = role
        refreshView()
    }

    private fun refreshView() {
        val message = lastMessage

        binding.present = message != null
        if(message == null) {
            return
        }

        when(message) {
            is TextMessage -> {
                binding.message = getAuthorText(message.author) + ": " + message.text
            }
            is ImageMessage -> {
                binding.message = getAuthorText(message.author) + ": " + "Wysłano zdjęcie"
            }
        }

        binding.time = message.sendTime.getPrettyTime()
    }

    private fun getAuthorText(author: MessageAuthor): String {
        return if(role == Role.EXPERT && author == MessageAuthor.EXPERT ||
            role == Role.CLIENT && author == MessageAuthor.CLIENT) {
            "Ty"
        } else {
            when(author) {
                MessageAuthor.EXPERT -> "Wykonawca"
                MessageAuthor.CLIENT -> "Klient"
            }
        }
    }

}
