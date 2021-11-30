package com.klima7.services.common.components.offer

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.BindingMethod
import androidx.databinding.DataBindingUtil
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ViewOfferStatusToolbarBinding
import com.klima7.services.common.models.OfferStatus

@androidx.databinding.BindingMethods(
    value = [
        BindingMethod(
            type = OfferStatusToolbarView::class,
            attribute = "offerstatustoolbar_selection_listener",
            method = "setSelectionListener"
        ),
        BindingMethod(
            type = OfferStatusToolbarView::class,
            attribute = "offerstatustoolbar_offer_status",
            method = "setOfferStatus"
        ),
    ]
)
class OfferStatusToolbarViewBindingMethods

class OfferStatusToolbarView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewOfferStatusToolbarBinding
    private var actionBarHeight = 0f
    private var panel: View
    private var visible = true
    private var offerStatus: OfferStatus? = OfferStatus.NEW
    private var selectionListener: SelectionListener? = null

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

    fun setOfferStatus(offerStatus: OfferStatus?) {
        this.offerStatus = offerStatus
        refreshView()
    }

    fun setSelectionListener(selectionListener: SelectionListener?) {
        this.selectionListener = selectionListener
        refreshView()
    }

    private fun refreshView() {
        addSelectionListener(selectionListener)
        selectProperChip(offerStatus)
    }

    private fun addSelectionListener(listener: SelectionListener?) {
        binding.viewofferstatusChipCancelled.setOnClickListener {
            selectProperChip(offerStatus)
            if(offerStatus != OfferStatus.CANCELLED) {
                listener?.onOfferStatusSelected(OfferStatus.CANCELLED)
            }
        }

        binding.viewofferstatusChipInRealization.setOnClickListener {
            selectProperChip(offerStatus)
            if(offerStatus != OfferStatus.IN_REALIZATION) {
                listener?.onOfferStatusSelected(OfferStatus.IN_REALIZATION)
            }
        }

        binding.viewofferstatusChipDone.setOnClickListener {
            selectProperChip(offerStatus)
            if(offerStatus != OfferStatus.DONE) {
                listener?.onOfferStatusSelected(OfferStatus.DONE)
            }
        }
    }

    private fun selectProperChip(offerStatus: OfferStatus?) {
        val id = when(offerStatus) {
            OfferStatus.CANCELLED -> R.id.viewofferstatus_chip_cancelled
            OfferStatus.IN_REALIZATION -> R.id.viewofferstatus_chip_in_realization
            OfferStatus.DONE -> R.id.viewofferstatus_chip_done
            OfferStatus.NEW -> 0
            null -> 0
        }

        binding.viewofferstatusChips.check(id)
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
        bundle.putSerializable("offerStatus", this.offerStatus)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var superState: Parcelable? = null
        if (state is Bundle) {
            this.visible = state.getBoolean("visible")
            this.offerStatus = state.getSerializable("offerStatus") as OfferStatus
            superState = state.getParcelable("superState")
        }
        super.onRestoreInstanceState(superState)
    }

    interface SelectionListener {
        fun onOfferStatusSelected(offerStatus: OfferStatus)
    }
}
