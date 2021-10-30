package com.klima7.services.common.components.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingMethod
import com.klima7.services.common.R
import com.klima7.services.common.models.Failure
import com.klima7.services.common.ui.FailureDescription
import java.lang.IllegalStateException

@androidx.databinding.BindingMethods(
    value = [
        BindingMethod(
            type = LoadAreaView::class,
            attribute = "la_state",
            method = "setState"
        ),
        BindingMethod(
            type = LoadAreaView::class,
            attribute = "la_failure",
            method = "setFailure"
        ),
        BindingMethod(
            type = LoadAreaView::class,
            attribute = "la_onRefresh",
            method = "setOnRefreshListener"
        ),
    ]
)
class BindingMethods

class LoadAreaView : FrameLayout {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        attrs?.let { initTypedArray(it) } ?: initDefaultValues()
    }

    private var state: State? = null
    private var onRefreshListener: OnRefreshListener? = null

    private lateinit var main: View
    private lateinit var failure: View
    private lateinit var spinner: View
    private lateinit var touchInterceptor: View
    private lateinit var failureImage: ImageView
    private lateinit var failureMessage: TextView
    private lateinit var refreshButton: ImageButton

    private fun initTypedArray(attrs: AttributeSet) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.LoadArea, 0, 0)
        // Handle parameters
        ta.recycle()
    }

    private fun initDefaultValues() {
        // Handle parameters
    }

    fun setState(newState: State) {
        if(state == newState)
            return

        val newViewState = getViewState(newState)

        if(state == null) {
            goToViewStateInstant(newViewState)
        }
        else {
            goToViewStateAnimate(newViewState)
        }

        state = newState
    }

    fun setFailure(failure: Failure?) {
        if(failure == null)
            return

        val desc = FailureDescription.get(failure)
        failureMessage.text = resources.getString(desc.textId)
        failureImage.setImageResource(desc.imageId)
    }

    fun setOnRefreshListener(onRefreshListener: OnRefreshListener?) {
        this.onRefreshListener = onRefreshListener
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setup()
    }

    private fun setup() {
        if(childCount != 1) throw IllegalStateException("LoadAreaView must have only one child")
        main = getChildAt(0)
        val mixin = LayoutInflater.from(context).inflate(R.layout.part_load, this, false)
        addView(mixin)

        // Get views
        failure = mixin.findViewById(R.id.load_failure)
        touchInterceptor = mixin.findViewById(R.id.load_touch_interceptor)
        failureImage = mixin.findViewById(R.id.load_failure_image)
        failureMessage = mixin.findViewById(R.id.load_failure_message)
        refreshButton = mixin.findViewById(R.id.load_refresh_button)
        spinner = mixin.findViewById(R.id.load_spinner)

        // Prepare
        touchInterceptor.setOnTouchListener { _, _ -> true }
        refreshButton.setOnClickListener { refreshClicked() }
    }

    private fun refreshClicked() {
        showRefreshButtonAnimation()
        onRefreshListener?.refresh()
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

    private fun getViewState(state: State): ViewState {
        val main = when(state) {
            State.MAIN -> 1.0f
            State.PENDING -> 0.5f
            else -> 0.0f
        }
        val failure = if(state == State.FAILURE) 1.0f else 0.0f
        val spinner = if(state == State.LOAD || state == State.PENDING) 1.0f else 0.0f
        return ViewState(main, failure, spinner)
    }

    private fun goToViewStateInstant(viewState: ViewState) {
        main.setAlphaAndVisibility(viewState.mainAlpha)
        failure.setAlphaAndVisibility(viewState.failureAlpha)
        spinner.setAlphaAndVisibility(viewState.spinnerAlpha)
    }

    private fun goToViewStateAnimate(viewState: ViewState) {
        animateElement(main, viewState.mainAlpha)
        animateElement(failure, viewState.failureAlpha)
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

    private fun View.setAlphaAndVisibility(alpha: Float) {
        this.alpha = alpha
        this.visibility = if(alpha == 0.0f) View.GONE else View.VISIBLE
    }

    enum class State {
        LOAD, MAIN, PENDING, FAILURE;
    }

    interface OnRefreshListener {
        fun refresh()
    }

    private data class ViewState(
        val mainAlpha: Float,
        val failureAlpha: Float,
        val spinnerAlpha: Float
    )

}
