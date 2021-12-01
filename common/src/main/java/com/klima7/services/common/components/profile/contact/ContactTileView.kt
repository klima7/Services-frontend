package com.klima7.services.common.components.profile.contact

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingMethod
import androidx.databinding.DataBindingUtil
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ViewContactTileBinding

@androidx.databinding.BindingMethods(
    value = [
        BindingMethod(
            type = ContactTileView::class,
            attribute = "contactTile_subtitle",
            method = "setSubtitle"
        ),
        BindingMethod(
            type = ContactTileView::class,
            attribute = "contactTile_click_listener",
            method = "setClickListener"
        ),
    ]
)
class ContactTileViewBindingMethods

class ContactTileView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewContactTileBinding
    private var title: String? = null
    private var subtitle: String? = null
    private var icon: Drawable? = null
    private var clickListener: ClickListener? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_contact_tile, this, true)
        attrs?.let { initTypedArray(it) }
        refreshView()
    }

    private fun initTypedArray(attrs: AttributeSet) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.ContactTile, 0, 0)
        title = ta.getString(R.styleable.ContactTile_contactTile_title)
        subtitle = ta.getString(R.styleable.ContactTile_contactTile_subtitle)
        icon = ta.getDrawable(R.styleable.ContactTile_contactTile_icon)
        ta.recycle()
    }

    fun setSubtitle(subtitle: String?) {
        this.subtitle = subtitle
        refreshView()
    }

    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
        refreshView()
    }

    private fun refreshView() {
        binding.title = title
        binding.subtitle = subtitle
        binding.icon = icon
        binding.viewcontacttileContainer.setOnClickListener {
            clickListener?.clicked()
        }
    }

    interface ClickListener {
        fun clicked()
    }
}
