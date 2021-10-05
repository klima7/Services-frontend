package com.klima7.services.common.lib.failurable

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentFailurableWrapperBinding
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.common.lib.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FailurableWrapperFragment<DB: ViewDataBinding>(
    private val mainFragment: FailurableFragment<DB>
): BaseFragment<FragmentFailurableWrapperBinding>() {

    override val layoutId = R.layout.fragment_failurable_wrapper
    override val viewModel: FailurableWrapperViewModel by viewModel()

    override fun init() {
        childFragmentManager
            .beginTransaction()
            .add(R.id.failure_holder_main_fragment, mainFragment)
            .commit()
    }

    fun showFailure(failure: Failure) {
        viewModel.showFailure(failure)
    }

    fun showMain() {
        viewModel.showMain()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        when(event) {
            FailurableWrapperViewModel.Event.RefreshMainFragment -> refreshMainFragment()
        }
    }

    private fun refreshMainFragment() {
        mainFragment.refresh()
    }
}


class FailurableWrapperViewModel: BaseViewModel() {

    val errorVisible = MutableLiveData(false)

    sealed class Event: BaseEvent() {
        object RefreshMainFragment: Event()
    }

    fun showFailure(failure: Failure) {
        errorVisible.value = true
    }

    fun showMain() {
        errorVisible.value = false
    }

    fun refreshClicked() {
        sendEvent(Event.RefreshMainFragment)
    }

}