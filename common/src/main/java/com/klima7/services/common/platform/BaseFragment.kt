package com.klima7.services.common.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.klima7.services.common.BR
import com.klima7.services.common.R
import kotlinx.coroutines.flow.collect
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

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

    private fun finishActivity() {
        requireActivity().finish()
    }

    fun showToastSuccess(message: String, title: String = "Sukces") =
        showToast(MotionToastStyle.SUCCESS, message, title)

    private fun showToast(style: MotionToastStyle, message: String, title: String) {
        MotionToast.createColorToast(requireActivity(),
            title,
            message,
            style,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(requireActivity(), R.font.helvetica_regular))
    }
}