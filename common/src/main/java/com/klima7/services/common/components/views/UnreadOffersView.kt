package com.klima7.services.common.components.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ViewUnreadCountBinding

@BindingAdapter("unreadcount_count")
fun setUnreadCount(view: UnreadOffersView, count: Int?) {
    if(count != null) {
        view.setCount(count)
    }
}

class UnreadOffersView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewUnreadCountBinding
    private var count = 0

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_unread_count, this, true)
        refreshView()
    }

    fun setCount(count: Int) {
        this.count = count
        refreshView()
    }

    private fun refreshView() {
        binding.count = count.toString()
    }

}
