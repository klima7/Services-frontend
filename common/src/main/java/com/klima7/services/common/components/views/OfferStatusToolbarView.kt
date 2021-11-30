package com.klima7.services.common.components.views

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ViewOfferStatusToolbarBinding
import android.os.Bundle




class OfferStatusToolbarView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewOfferStatusToolbarBinding
    private var actionBarHeight = 0f
    private var panel: View
    private var visible = true

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_offer_status_toolbar, this, true)
        refreshView()

        val tv = TypedValue()
        if (binding.viewofferstatusPanel.context.theme.resolveAttribute(
                android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue
                .complexToDimensionPixelSize(tv.data, resources.displayMetrics).toFloat()
        }
        panel = binding.viewofferstatusPanel
    }

    private fun refreshView() {

    }

    fun setVisibleInstant(newVisible: Boolean) {
        panel.translationY = if(newVisible) 0f else -actionBarHeight
        this.visible = newVisible
    }

    fun setVisibleAnimate(newVisible: Boolean) {
        if(this.visible == newVisible) {
            return
        }

        panel.clearAnimation()
        this.visible = newVisible

        if(newVisible) {
            panel.translationY = -actionBarHeight
            panel.animate().translationY(0f)
        }
        else {
            panel.translationY = 0f
            panel.animate().translationY(-actionBarHeight)
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable("superState", super.onSaveInstanceState())
        bundle.putBoolean("visible", this.visible)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var superState: Parcelable? = null
        if (state is Bundle) {
            this.visible = state.getBoolean("visible")
            superState = state.getParcelable("superState")
        }
        super.onRestoreInstanceState(superState)
    }

    interface ClickListener {
        fun clicked()
    }
}
