package com.klima7.services.common.lib.failfrag

import androidx.databinding.ViewDataBinding
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.common.lib.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

abstract class FailurableFragment<DB: ViewDataBinding>: BaseFragment<DB>() {

    abstract override val viewModel: FailurableViewModel
    private val parentViewModel: FailurableWrapperViewModel by lazy {
        requireParentFragment().getViewModel()
    }

    fun refresh() {
        viewModel.refresh()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        when(event) {
            is FailurableViewModel.FailurableEvent.ShowFailureEvent -> showFailure(event.failure)
            is FailurableViewModel.FailurableEvent.ShowMainEvent -> showMain()
            is FailurableViewModel.FailurableEvent.ShowLoadingEvent -> showLoading()
            is FailurableViewModel.FailurableEvent.ShowPendingEvent -> showPending()
        }
    }

    private fun showFailure(failure: Failure) {
        parentViewModel.showFailure(failure)
    }

    private fun showMain() {
        parentViewModel.showMain()
    }

    private fun showLoading() {
        parentViewModel.showLoading()
    }

    private fun showPending() {
        parentViewModel.showPending()
    }
}