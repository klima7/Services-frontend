package com.klima7.services.common.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.klima7.services.common.R
import com.klima7.services.common.ui.utils.FailureDescription
import com.klima7.services.common.ui.utils.observeOnce

abstract class BaseLoadFragment<DB: ViewDataBinding>: BaseFragment<DB>() {

    abstract override val viewModel: BaseLoadViewModel

    private lateinit var failurePart: View
    private lateinit var mainPart: View
    private lateinit var spinner: View
    private lateinit var touchInterceptor: View
    private lateinit var failureImage: ImageView
    private lateinit var failureMessage: TextView
    private lateinit var refreshButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainPart = super.onCreateView(inflater, container, savedInstanceState)!!
        val frame = FrameLayout(requireContext())
        val mixin = LayoutInflater.from(context).inflate(R.layout.part_load, frame, false)
        frame.addView(mainPart)
        frame.addView(mixin)
        return frame
    }

    override fun init() {
        super.init()

        // Get views
        failurePart = requireView().findViewById(R.id.load_failure)
        mainPart = binding.root
        touchInterceptor = requireView().findViewById(R.id.load_touch_interceptor)
        failureImage = requireView().findViewById(R.id.load_failure_image)
        failureMessage = requireView().findViewById(R.id.load_failure_message)
        refreshButton = requireView().findViewById(R.id.load_refresh_button)
        spinner = requireView().findViewById(R.id.load_spinner)

        // Prepare
        touchInterceptor.setOnTouchListener { _, _ -> true }
        refreshButton.setOnClickListener { viewModel.refreshClicked() }

        // Observe
        viewModel.loadViewState.observeOnce(viewLifecycleOwner) { viewState ->
            instantUpdate(viewState)
            viewModel.loadViewState.observe(viewLifecycleOwner) { newViewState ->
                animateToViewState(newViewState)
            }
        }

        viewModel.loadFailure.observe(viewLifecycleOwner) {
            it?.let {  failure ->
                val desc = FailureDescription.get(failure)
                failureMessage.text = resources.getString(desc.textId)
                failureImage.setImageResource(desc.imageId)
            }
        }

        viewModel.loadInteraction.observe(viewLifecycleOwner) { enabled ->
            touchInterceptor.visibility = if(enabled) View.INVISIBLE else View.VISIBLE
        }

        viewModel.loadPending.observe(viewLifecycleOwner) { pending ->
            refreshButton.isEnabled = !pending
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        when(event) {
            is BaseLoadViewModel.LoadEvent.ShowRefreshButtonAnimation ->
                showRefreshButtonAnimation()
        }
    }

    private fun showRefreshButtonAnimation() {
        refreshButton.apply {
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

    private fun instantUpdate(viewState: BaseLoadViewModel.LoadViewState) {
        mainPart.setAlphaAndVisibility(viewState.mainAlpha)
        failurePart.setAlphaAndVisibility(viewState.failureAlpha)
        spinner.setAlphaAndVisibility(viewState.spinnerAlpha)
    }

    private fun View.setAlphaAndVisibility(alpha: Float) {
        this.alpha = alpha
        this.visibility = if(alpha == 0.0f) View.INVISIBLE else View.VISIBLE
    }

    private fun animateToViewState(viewState: BaseLoadViewModel.LoadViewState) {
        animateElement(mainPart, viewState.mainAlpha)
        animateElement(failurePart, viewState.failureAlpha)
        animateElement(spinner, viewState.spinnerAlpha)
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
}