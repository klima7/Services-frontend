package com.klima7.services.common.components.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.klima7.services.common.R
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
        binding.loadlistRecycler.adapter = adapter
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
}
