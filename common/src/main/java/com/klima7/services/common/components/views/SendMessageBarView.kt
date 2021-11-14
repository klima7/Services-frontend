package com.klima7.services.common.components.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ViewSendMessageBarBinding

class SendMessageBarView: FrameLayout {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var binding: ViewSendMessageBarBinding
    private var listener: Listener? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_send_message_bar, this, true)

        binding.msgbarSendButton.apply {
            isEnabled = false
            setOnClickListener { listener?.onSendMessageClicked(this@SendMessageBarView) }
        }

        binding.msgbarImageIcon.setOnClickListener {
            listener?.onSelectImageClicked(this)
        }

        binding.msgbarText.addTextChangedListener { it ->
            val empty = it.isNullOrBlank()
            binding.msgbarImageIcon.visibility = if(empty) View.VISIBLE else View.GONE
            binding.msgbarSendButton.isEnabled = !empty
        }
    }

    var text: String
        get() = binding.msgbarText.text.toString()
        set(value) {
            binding.msgbarText.setText(value)
        }

    fun clear() {
        text = ""
    }

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    interface Listener {
        fun onSendMessageClicked(smb: SendMessageBarView)
        fun onSelectImageClicked(smb: SendMessageBarView)
    }

}