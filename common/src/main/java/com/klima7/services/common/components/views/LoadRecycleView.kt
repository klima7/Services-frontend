package com.klima7.services.common.components.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ElementLoadListStateBinding
import com.klima7.services.common.databinding.ViewLoadListBinding
import java.util.NoSuchElementException


class LoadRecycleView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var binding: ViewLoadListBinding

    private var mAdapter: PagingDataAdapter<*, *>? = null

    init {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.view_load_list, this, true)
        init()
    }

    private fun init() {
        binding.loadlistRefreshButton.setOnClickListener {
            mAdapter?.apply {
                refresh()
            }
        }

        binding.loadlistSwipeRefresh.setOnRefreshListener {
            binding.loadlistSwipeRefresh.isRefreshing = false
            adapter?.refresh()
        }
    }

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

    private fun setAdapterHelper(adapter: PagingDataAdapter<*, *>) {
        this.mAdapter = adapter
        addLoadStateListener(adapter)
        val a2 = adapter.withLoadStateFooter(
            footer = SimpleLoadStateAdapter { adapter.retry() }
        )
        binding.loadlistRecycler.adapter = a2
    }

    private fun addLoadStateListener(adapter: PagingDataAdapter<*, *>) {
        adapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {
                binding.loadlistFailure.visibility = View.GONE
                binding.loadlistSpinner.visibility = View.VISIBLE
            }
            else {
                binding.loadlistSpinner.visibility = View.GONE
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> {
                        binding.loadlistFailure.visibility = View.VISIBLE
                        loadState.refresh as LoadState.Error
                    }
                    else -> null
                }
                errorState?.let {
                    Log.e("secret", "error", it.error)
                    Toast.makeText(context, it.error.message, Toast.LENGTH_LONG).show()
                    when(it.error) {
                        is NoSuchElementException -> Log.i("secret", "Empty list")
                        else -> Log.i("secret", "")
                    }
                }
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
}
