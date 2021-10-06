package com.klima7.services.common.lib.failurable

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentFailurableWrapperBinding
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.common.lib.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FailurableWrapperFragment<DB: ViewDataBinding>(
    private val mainFragment: FailurableFragment<DB>? = null
): BaseFragment<FragmentFailurableWrapperBinding>() {

    override val layoutId = R.layout.fragment_failurable_wrapper
    override val viewModel: FailurableWrapperViewModel by viewModel()

    private data class FailureDescription(val textId: Int, val imageId: Int)

    private val failureDescriptions = mapOf(
        Failure.InternetFailure to
                FailureDescription(R.string.internet_failure_message, R.drawable.icon_error_no_internet),
        Failure.ServerFailure to
                FailureDescription(R.string.server_failure_message, R.drawable.icon_error_server),
        Failure.UnknownFailure to
                FailureDescription(R.string.unknown_failure_message, R.drawable.icon_error_unknown),
        Failure.PermissionFailure to
                FailureDescription(R.string.permission_failure_message, R.drawable.icon_error_permission),
        Failure.NotFoundFailure to
                FailureDescription(R.string.not_found_failure_message, R.drawable.icon_error_not_found),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(savedInstanceState == null) {
            childFragmentManager
                .beginTransaction()
                .add(R.id.failure_holder_main_fragment, mainFragment!!)
                .commit()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun init() {
        viewModel.currentFailure.observe(viewLifecycleOwner) {
            it?.let {  failure ->
                failureDescriptions[failure]?.let { desc ->
                    binding.message = resources.getString(desc.textId)
                    binding.image = desc.imageId
                }
            }
        }
    }

    fun showFailure(failure: Failure) {
        Log.i("Hello", "showFailure in FailurableWrapper fragment")
        viewModel.showFailure(failure)
    }

    fun showMain() {
        viewModel.showMain()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            FailurableWrapperViewModel.Event.RefreshMainFragment -> refreshMainFragment()
        }
    }

    private fun refreshMainFragment() {
        mainFragment?.refresh()
    }
}


class FailurableWrapperViewModel: BaseViewModel() {

    val errorVisible = MutableLiveData(false)
    val pendingRefresh = MutableLiveData(false)
    val currentFailure = MutableLiveData<Failure?>(null)

    sealed class Event: BaseEvent() {
        object RefreshMainFragment: Event()
    }

    fun showFailure(failure: Failure) {
        Log.i("Hello", "showFailure")
        currentFailure.value = failure
        errorVisible.value = true
        pendingRefresh.value = false
    }

    fun showMain() {
        Log.i("Hello", "showMain")
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