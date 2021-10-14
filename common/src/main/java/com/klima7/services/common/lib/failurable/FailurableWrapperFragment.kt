package com.klima7.services.common.lib.failurable

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.ViewDataBinding
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentFailurableWrapperBinding
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.common.lib.utils.FailureDescription
import com.klima7.services.common.lib.utils.replaceFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FailurableWrapperFragment<DB: ViewDataBinding>(
    private val mainFragment: FailurableFragment<DB>? = null
): BaseFragment<FragmentFailurableWrapperBinding>() {

    override val layoutId = R.layout.fragment_failurable_wrapper
    override val viewModel: FailurableWrapperViewModel by viewModel()

    override fun init() {
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

    fun showFailure(failure: Failure) {
        viewModel.showFailure(failure)
    }

    fun showMain() {
        viewModel.showMain()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            FailurableWrapperViewModel.Event.RefreshMainFragment -> refreshMainFragment()
            FailurableWrapperViewModel.Event.StartShowMainAnimation -> showAnimation(false)
            FailurableWrapperViewModel.Event.StartShowFailureAnimation -> showAnimation(true)
        }
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