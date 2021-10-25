package com.klima7.services.common.components.delete

import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentDeleteBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel


abstract class BaseDeleteFragment(
    private val subtitle: Int
): BaseFragment<FragmentDeleteBinding>() {

    override val layoutId = R.layout.fragment_delete
    abstract override val viewModel: BaseDeleteViewModel

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            BaseDeleteViewModel.Event.ShowSplashScreen -> showSplashScreen()
            BaseDeleteViewModel.Event.Finish -> finish()
            is BaseDeleteViewModel.Event.ShowDeleteFailure -> showDeleteFailure()
        }
    }

    private fun showDeleteFailure() {

    }

    private fun finish() {
        requireActivity().finish()
    }

    protected abstract fun showSplashScreen()

}
