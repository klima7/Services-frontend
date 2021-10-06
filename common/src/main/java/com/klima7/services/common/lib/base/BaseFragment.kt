package com.klima7.services.common.lib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import com.klima7.services.common.BR

abstract class BaseFragment<DB: ViewDataBinding>: Fragment() {

    protected abstract val layoutId: Int
    protected abstract val viewModel: BaseViewModel
    protected lateinit var binding: DB

    val TAG: String = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(savedInstanceState == null)
            onFirstCreation()
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doDataBinding()
        startHandlingEvents()
        init()
    }

    private fun doDataBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.viewmodel, viewModel)
        binding.executePendingBindings()
    }

    private fun startHandlingEvents() {
        lifecycleScope.launchWhenStarted {
            viewModel.eventFlow.collect { event ->
                handleEvent(event)
            }
        }
    }

    open suspend fun handleEvent(event: BaseViewModel.BaseEvent) {}

    open fun init() {}

    open fun onFirstCreation() {}
}