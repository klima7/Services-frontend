package com.klima7.services.common.components.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingMethod
import androidx.databinding.DataBindingUtil
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ViewSettingsTileBinding

@androidx.databinding.BindingMethods(
    value = [
        BindingMethod(
            type = SettingsTileView::class,
            attribute = "settingstile_action",
            method = "setAction"
        ),
    ]
)
class SettingsTileViewBindingMethods

class SettingsTileView : FrameLayout {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        attrs?.let { initTypedArray(it) }
        refreshView()
    }

    private var binding: ViewSettingsTileBinding
    private var title: String = ""
    private var iconId: Int = R.drawable.logo
    private var action: OnClickListener? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_settings_tile, this, true)
        refreshView()
    }

    fun setAction(action: OnClickListener?) {
        this.action = action
        refreshView()
    }

    private fun initTypedArray(attrs: AttributeSet) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.SettingsTile, 0, 0)
        title = ta.getString(R.styleable.SettingsTile_settingstile_title) ?: title
        iconId = ta.getResourceId(R.styleable.SettingsTile_settingstile_icon, iconId)
        ta.recycle()
    }

    private fun refreshView() {
        binding.settingstileTitle.text = title
        binding.settingstileIcon.setImageResource(iconId)
        binding.settingstileCard.setOnClickListener(action)
    }

}
