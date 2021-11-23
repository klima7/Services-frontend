package com.klima7.services.common.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.klima7.services.common.BR
import kotlinx.coroutines.flow.collect

abstract class BaseFragment<DB: ViewDataBinding>: Fragment() {

    protected abstract val layoutId: Int
    protected abstract val viewModel: BaseViewModel
    protected lateinit var binding: DB

    val TAG: String = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        if(savedInstanceState == null)
            onFirstCreation()
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

    open suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        when(event) {
            BaseViewModel.BaseEvent.FinishActivity -> finishActivity()
        }
    }

    open fun init() {}

    open fun onFirstCreation() {}

    fun showShortToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    protected fun finishActivity() {
        requireActivity().finish()
    }
}