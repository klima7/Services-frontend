package com.klima7.services.common.lib.faildialog

import androidx.lifecycle.ViewModel
import com.klima7.services.common.domain.models.Failure

class FailureDialogViewModel: ViewModel() {

    private lateinit var mRequestKey: String
    private lateinit var mMessage: String
    private lateinit var mFailure: Failure
    private var mRetryAbility: Boolean = false

    fun setData(requestKey: String, message: String, failure: Failure, retryAbility: Boolean) {
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