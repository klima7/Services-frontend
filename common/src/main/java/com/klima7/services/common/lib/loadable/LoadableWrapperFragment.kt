package com.klima7.services.common.lib.loadable

import android.annotation.SuppressLint
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.databinding.ViewDataBinding
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentFailurableWrapperBinding
import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.common.lib.utils.FailureDescription
import com.klima7.services.common.lib.utils.observeOnce
import com.klima7.services.common.lib.utils.replaceFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoadableWrapperFragment<DB: ViewDataBinding>(
    private val mainFragment: LoadableFragment<DB>? = null
): BaseFragment<FragmentFailurableWrapperBinding>() {

    override val layoutId = R.layout.fragment_failurable_wrapper
    override val viewModel: LoadableWrapperViewModel by viewModel()

    @SuppressLint("ClickableViewAccessibility")
    override fun init() {
        binding.failureHolderTouchInterceptor.setOnTouchListener { _, _ -> true }

        viewModel.viewState.observeOnce(viewLifecycleOwner) { viewState ->
            instantUpdate(viewState)
            viewModel.viewState.observe(viewLifecycleOwner) { newViewState ->
                animateToViewState(newViewState)
            }
        }

        viewModel.currentFailure.observe(viewLifecycleOwner) {
            it?.let {  failure ->
                val desc = FailureDescription.get(failure)
                binding.message = resources.getString(desc.textId)
                binding.image = desc.imageId
            }
        }
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        replaceFragment(R.id.failure_holder_main_fragment, mainFragment!!)
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is LoadableWrapperViewModel.Event.RefreshMainFragment -> refreshMainFragment()
            is LoadableWrapperViewModel.Event.ShowRefreshButtonAnimation -> showRefreshButtonAnimation()
        }
    }

    private fun instantUpdate(viewState: LoadableWrapperViewModel.ViewState) {
        binding.failureHolderMainFragment.setAlphaAndVisibility(viewState.mainAlpha)
        binding.failureHolderFailureView.setAlphaAndVisibility(viewState.failureAlpha)
        binding.failureHolderSpinnerAnimation.setAlphaAndVisibility(viewState.spinnerAlpha)
    }

    private fun animateToViewState(viewState: LoadableWrapperViewModel.ViewState) {
        animateElement(binding.failureHolderMainFragment, viewState.mainAlpha)
        animateElement(binding.failureHolderFailureView, viewState.failureAlpha)
        animateElement(binding.failureHolderSpinnerAnimation, viewState.spinnerAlpha)
    }

    private fun animateElement(view: View, targetAlpha: Float) {
        view.clearAnimation()
        view.animate().apply {
            interpolator = LinearInterpolator()
            duration = resources.getInteger(R.integer.failure_fade_time).toLong()
            alpha(targetAlpha)
            withStartAction { view.visibility = View.VISIBLE }
            withEndAction { view.setAlphaAndVisibility(targetAlpha) }
            start()
        }
    }

    private fun View.setAlphaAndVisibility(alpha: Float) {
        this.alpha = alpha
        this.visibility = if(alpha == 0.0f) View.INVISIBLE else View.VISIBLE
    }

    private fun refreshMainFragment() {
        val mainFragment = childFragmentManager.findFragmentById(R.id.failure_holder_main_fragment) as? LoadableFragment<*>
        mainFragment?.let {
            mainFragment.refresh()
        }
    }

    private fun showRefreshButtonAnimation() {
        binding.failureHolderRefreshButton.apply {
            clearAnimation()
            rotation = 0.0f
            animate().apply {
                interpolator = LinearInterpolator()
                duration = 500
                rotationBy(360.0f)
                start()
            }
        }
    }

}