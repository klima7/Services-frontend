package com.klima7.services.common.components.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.databinding.BindingMethod
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ElementLoadListStateBinding
import com.klima7.services.common.databinding.ViewLoadListBinding

@androidx.databinding.BindingMethods(
    value = [
        BindingMethod(
            type = LoadRecyclerView::class,
            attribute = "lr_onRefresh",
            method = "setOnRefreshListener"
        ),
    ]
)
class LoadRecyclerViewBindingMethods

class LoadRecyclerView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewLoadListBinding

    private var mAdapter: PagingDataAdapter<*, *>? = null
    private var onRefreshListener: OnRefreshListener? = null

    private var empty = false
    private var state = State.STANDARD

    private enum class State {
        LOAD, FAILURE, STANDARD
    }

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_load_list, this, true)
        init()
    }

    private fun init() {
        binding.loadlistRefreshButton.setOnClickListener {
            mAdapter?.apply {
                onRefreshListener?.refresh()
            }
        }
    }

    val recycler: RecyclerView
    get() = binding.loadlistRecycler

    var adapter: PagingDataAdapter<*, *>?
        get() = mAdapter
        set(adapter) {
            if (adapter != null) {
                setAdapterHelper(adapter)
            }
        }

    var layoutManager: RecyclerView.LayoutManager?
    get() = binding.loadlistRecycler.layoutManager
    set(value) {
        binding.loadlistRecycler.layoutManager = value
    }

    fun setOnRefreshListener(onRefreshListener: OnRefreshListener?) {
        this.onRefreshListener = onRefreshListener
    }

    private fun setAdapterHelper(adapter: PagingDataAdapter<*, *>) {
        this.mAdapter = adapter
        addLoadStateListener(adapter)
        val wrapperAdapter = adapter.withLoadStateFooter(
            footer = SimpleLoadStateAdapter { adapter.retry() }
        )
        adapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                if(adapter.itemCount == 0) {
                    empty = true
                }
                updateView()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                empty = false
                updateView()
            }
        })
        binding.loadlistRecycler.adapter = wrapperAdapter
    }

    private fun addLoadStateListener(adapter: PagingDataAdapter<*, *>) {
        adapter.addLoadStateListener { loadState ->
            state = when (loadState.refresh) {
                is LoadState.Loading -> State.LOAD
                is LoadState.Error -> State.FAILURE
                else -> State.STANDARD
            }
            updateView()
        }
    }

    private fun updateView() {
        if(state == State.STANDARD && empty) {
            binding.loadlistEmpty.visibility = View.VISIBLE
            binding.loadlistFailure.visibility = View.GONE
            binding.loadlistSpinner.visibility = View.GONE
        }
        else {
            binding.loadlistEmpty.visibility = View.GONE
            if (state == State.STANDARD) {
                binding.loadlistSpinner.visibility = View.GONE
                binding.loadlistFailure.visibility = View.GONE
            } else if (state == State.LOAD) {
                binding.loadlistFailure.visibility = View.GONE
                binding.loadlistSpinner.visibility = View.VISIBLE
            } else if (state == State.FAILURE) {
                binding.loadlistSpinner.visibility = View.GONE
                binding.loadlistFailure.visibility = View.VISIBLE
            }
        }
    }

    class SimpleLoadStateAdapter(
        private val retry: () -> Unit
    ) : LoadStateAdapter<SimpleLoadStateAdapter.LoadStateViewHolder>() {

        override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

            val progress = holder.loadStateViewBinding.loadStateProgress
            val retry = holder.loadStateViewBinding.loadStateRetry
            val message = holder.loadStateViewBinding.loadStateErrorMessage

            retry.isVisible = loadState !is LoadState.Loading
            message.isVisible = loadState !is LoadState.Loading
            progress.isVisible = loadState is LoadState.Loading

            retry.setOnClickListener {
                this.retry.invoke()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
            return LoadStateViewHolder(
                ElementLoadListStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }

        class LoadStateViewHolder(val loadStateViewBinding: ElementLoadListStateBinding) :
            RecyclerView.ViewHolder(loadStateViewBinding.root)
    }

    interface OnRefreshListener {
        fun refresh()
    }
}
