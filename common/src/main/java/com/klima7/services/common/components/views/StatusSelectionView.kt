package com.klima7.services.common.components.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ViewStatusSelectionBinding
import com.klima7.services.common.models.OfferStatus

@BindingAdapter("statusSelectionView_statuses")
fun setStatuses(view: StatusSelectionView, newStatuses: Set<OfferStatus>?) {
    val currentStatuses = view.getSelected()
    if(newStatuses != null && currentStatuses != newStatuses) {
        view.setSelected(newStatuses)
    }
}

@InverseBindingAdapter(attribute = "statusSelectionView_statuses")
fun getStatuses(view: StatusSelectionView) = view.getSelected()

@BindingAdapter( "statusSelectionView_statusesAttrChanged")
fun setStatusesListener(view: StatusSelectionView, attrChange: InverseBindingListener) {
    view.setListener(object: StatusSelectionView.Listener {
        override fun statusSelectionChanged(selected: Set<OfferStatus>) {
            attrChange.onChange()
        }
    })
}

class StatusSelectionView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewStatusSelectionBinding
    private var listener: Listener? = null
    private var selected = emptySet<OfferStatus>()

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_status_selection, this, true)
        refreshView()
    }

    fun setSelected(selected: Set<OfferStatus>) {
        this.selected = selected
        listener?.statusSelectionChanged(selected)
        refreshView()
    }

    fun getSelected(): Set<OfferStatus> {
        return selected
    }

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    private fun refreshView() {
        addSelectionListeners()
        selectProperChips()
    }

    private fun addSelectionListeners() {
        binding.viewstatusselectionChipNew.setOnClickListener { selectionChanged() }
        binding.viewstatusselectionChipCancelled.setOnClickListener { selectionChanged() }
        binding.viewstatusselectionChipInRealization.setOnClickListener { selectionChanged() }
        binding.viewstatusselectionChipDone.setOnClickListener { selectionChanged() }
    }

    private fun selectionChanged() {
        selected = getSelectedStatuses()
        listener?.statusSelectionChanged(selected)
    }

    private fun selectProperChips() {
        binding.apply {
            viewstatusselectionChipNew.isChecked = selected.contains(OfferStatus.NEW)
            viewstatusselectionChipCancelled.isChecked = selected.contains(OfferStatus.CANCELLED)
            viewstatusselectionChipInRealization.isChecked = selected.contains(OfferStatus.IN_REALIZATION)
            viewstatusselectionChipDone.isChecked = selected.contains(OfferStatus.DONE)
        }
    }

    private fun getSelectedStatuses(): Set<OfferStatus> {
        val statuses = mutableSetOf<OfferStatus>()
        binding.apply {
            if(viewstatusselectionChipNew.isChecked) statuses.add(OfferStatus.NEW)
            if(viewstatusselectionChipCancelled.isChecked) statuses.add(OfferStatus.CANCELLED)
            if(viewstatusselectionChipInRealization.isChecked) statuses.add(OfferStatus.IN_REALIZATION)
            if(viewstatusselectionChipDone.isChecked) statuses.add(OfferStatus.DONE)
        }
        return statuses
    }

    interface Listener {
        fun statusSelectionChanged(selected: Set<OfferStatus>)
    }
}
