package com.klima7.services.common.lib.failfrag

import androidx.databinding.ViewDataBinding
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.common.lib.base.BaseViewModel

abstract class FailurableFragment<DB: ViewDataBinding>: BaseFragment<DB>() {

    abstract override val viewModel: FailurableViewModel

    fun refresh() {
        viewModel.refresh()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        when(event) {
            is FailurableViewModel.FailurableEvent.ShowFailureEvent -> showFailure(event.failure)
            is FailurableViewModel.FailurableEvent.ShowMainEvent -> showMain()
        }
    }

    private fun showFailure(failure: Failure) {
        val parent = parentFragment as? FailurableWrapperFragment<*>
        parent?.showFailure(failure)
    }

    private fun showMain() {
        val parent = parentFragment as? FailurableWrapperFragment<*>
        parent?.showMain()
    }
}