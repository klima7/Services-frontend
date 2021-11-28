package com.klima7.services.common.components.profile

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ViewProfileHeaderBinding

class ProfileHeaderView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewProfileHeaderBinding
    private var title = ""

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_profile_header, this, true)
        attrs?.let { initTypedArray(it) }
        refreshView()
    }

    private fun initTypedArray(attrs: AttributeSet) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.ProfileHeader, 0, 0)
        title = ta.getString(R.styleable.ProfileHeader_profileHeader_title) ?: title
        ta.recycle()
    }

    private fun refreshView() {
        binding.title = title
    }
}
