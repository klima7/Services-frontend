package com.klima7.services.common.ui.loadable

import androidx.databinding.ViewDataBinding
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.ui.base.BaseFragment
import com.klima7.services.common.ui.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

abstract class LoadableFragment<DB: ViewDataBinding>: BaseFragment<DB>() {

    abstract override val viewModel: LoadableViewModel
    private val parentViewModel: LoadableWrapperViewModel by lazy {
        requireParentFragment().getViewModel()
    }

    fun refresh() {
        viewModel.refresh()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        when(event) {
            is LoadableViewModel.FailurableEvent.ShowFailureEvent -> showFailure(event.failure)
            is LoadableViewModel.FailurableEvent.ShowMainEvent -> showMain()
            is LoadableViewModel.FailurableEvent.ShowLoadingEvent -> showLoading()
            is LoadableViewModel.FailurableEvent.ShowPendingEvent -> showPending()
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