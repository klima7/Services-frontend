package com.klima7.services.common.components.faildialog

import androidx.lifecycle.ViewModel
import com.klima7.services.common.models.Failure

class FailureDialogViewModel: ViewModel() {

    private lateinit var mRequestKey: String
    private lateinit var mMessage: String
    private var mFailure: Failure? = null
    private var mRetryAbility = false

    fun setData(requestKey: String, message: String, failure: Failure?, retryAbility: Boolean) {
        mRequestKey = requestKey
        mMessage = message
        mFailure = failure
        mRetryAbility = retryAbility
    }

    val requestKey get() = mRequestKey
    val message get() = mMessage
    val failure get() = mFailure
    val retryAbility get() = mRetryAbility

}