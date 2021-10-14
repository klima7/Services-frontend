package com.klima7.services.common.lib.failfrag

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.databinding.ViewDataBinding
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentFailurableWrapperBinding
import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.common.lib.utils.FailureDescription
import com.klima7.services.common.lib.utils.observeOnce
import com.klima7.services.common.lib.utils.replaceFragment
import com.klima7.services.common.lib.utils.setEnabledRecursive
import org.koin.androidx.viewmodel.ext.android.viewModel

class FailurableWrapperFragment<DB: ViewDataBinding>(
    private val mainFragment: FailurableFragment<DB>? = null
): BaseFragment<FragmentFailurableWrapperBinding>() {

    override val layoutId = R.layout.fragment_failurable_wrapper
    override val viewModel: FailurableWrapperViewModel by viewModel()

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
            is FailurableWrapperViewModel.Event.RefreshMainFragment -> refreshMainFragment()
        }
    }

    private fun instantUpdate(viewState: FailurableWrapperViewModel.ViewState) {
        binding.failureHolderMainFragment.setAlphaAndVisibility(viewState.mainAlpha)
        binding.failureHolderFailureView.setAlphaAndVisibility(viewState.failureAlpha)
        binding.failureHolderSpinnerAnimation.setAlphaAndVisibility(viewState.spinnerAlpha)
    }

    private fun animateToViewState(viewState: FailurableWrapperViewModel.ViewState) {
        Log.i("Hello", "Animate to $viewState")
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
        val mainFragment = childFragmentManager.findFragmentById(R.id.failure_holder_main_fragment) as? FailurableFragment<*>
        mainFragment?.let {
            mainFragment.refresh()
        }
    }

    private fun showAnimation(isShowFailure: Boolean) {
        binding.failureHolderMainFragment.clearAnimation()

        val mainAnimationR = if(isShowFailure) R.anim.failure_hide_animation else R.anim.failure_show_animation
        val failureAnimationR = if(!isShowFailure) R.anim.failure_hide_animation else R.anim.failure_show_animation

        val mainAnimation = AnimationUtils.loadAnimation(requireContext(), mainAnimationR)
        binding.failureHolderMainFragment.startAnimation(mainAnimation)
        mainAnimation.setAnimationListener(VisibilityAnimationListener(binding.failureHolderMainFragment, isShowFailure))

        val failureAnimation = AnimationUtils.loadAnimation(requireContext(), failureAnimationR)
        binding.failureHolderFailureView.startAnimation(failureAnimation)
        failureAnimation.setAnimationListener(VisibilityAnimationListener(binding.failureHolderFailureView, !isShowFailure))
    }

}

private class VisibilityAnimationListener(
    private val view: View,
    private val isInitiallyVisible: Boolean,
): Animation.AnimationListener {

    override fun onAnimationStart(animation: Animation?) {
        if(!isInitiallyVisible) view.visibility = View.VISIBLE
    }

    override fun onAnimationEnd(animation: Animation?) {
        view.visibility = if(isInitiallyVisible) View.INVISIBLE else View.VISIBLE
    }

    override fun onAnimationRepeat(animation: Animation?) { }
}