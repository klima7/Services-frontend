package com.klima7.services.common.lib.failurable

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import com.google.rpc.context.AttributeContext
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

    private data class FailureDescription(val textId: Int, val imageId: Int)

    private val failureDescriptions = mapOf(
        Failure.InternetFailure to
                FailureDescription(R.string.internet_failure_message, R.drawable.icon_error),
        Failure.ServerFailure to
                FailureDescription(R.string.server_failure_message, R.drawable.icon_error),
        Failure.UnknownFailure to
                FailureDescription(R.string.unknown_failure_message, R.drawable.icon_error),
        Failure.PermissionFailure to
                FailureDescription(R.string.permission_failure_message, R.drawable.icon_error),
        Failure.NotFoundFailure to
                FailureDescription(R.string.not_found_failure_message, R.drawable.icon_error),
    )


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
            is FailurableWrapperViewModel.Event.SetFailureMessage -> setFailureMessage(event.failure)
        }
    }

    private fun refreshMainFragment() {
        mainFragment.refresh()
    }

    private fun setFailureMessage(failure: Failure) {
        failureDescriptions[failure]?.let { desc ->
            binding.message = resources.getString(desc.textId)
        }
    }
}


class FailurableWrapperViewModel: BaseViewModel() {

    val errorVisible = MutableLiveData(false)
    val pendingRefresh = MutableLiveData(false)

    sealed class Event: BaseEvent() {
        object RefreshMainFragment: Event()
        class SetFailureMessage(val failure: Failure): Event()
    }

    fun showFailure(failure: Failure) {
        sendEvent(Event.SetFailureMessage(failure))
        errorVisible.value = true
        pendingRefresh.value = false
    }

    fun showMain() {
        errorVisible.value = false
        pendingRefresh.value = false
    }

    fun refreshClicked() {
        pendingRefresh.value?.let { value ->
            if(!value) {
                sendEvent(Event.RefreshMainFragment)
                pendingRefresh.value = true
            }
        }

    }

}