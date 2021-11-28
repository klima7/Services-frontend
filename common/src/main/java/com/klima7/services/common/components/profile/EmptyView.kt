package com.klima7.services.common.components.profile

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingMethod
import androidx.databinding.DataBindingUtil
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ViewEmptyBinding

@androidx.databinding.BindingMethods(
    value = [
        BindingMethod(
            type = EmptyView::class,
            attribute = "empty_visible",
            method = "setVisible"
        ),
    ]
)
class EmptyViewBindingMethods

class EmptyView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewEmptyBinding
    private var message = ""
    private var visible = true

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_empty, this, true)
        attrs?.let { initTypedArray(it) }
        refreshView()
    }

    private fun initTypedArray(attrs: AttributeSet) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.Empty, 0, 0)
        message = ta.getString(R.styleable.Empty_empty_message) ?: message
        ta.recycle()
    }

    fun setVisible(visible: Boolean) {
        this.visible = visible
        refreshView()
    }

    private fun refreshView() {
        binding.message = message
        binding.visible = visible
    }
}
