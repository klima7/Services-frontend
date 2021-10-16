package com.klima7.services.common.lib.faildialog

import androidx.lifecycle.ViewModel
import com.klima7.services.common.domain.models.Failure

class FailureDialogViewModel: ViewModel() {

    private lateinit var mRequestKey: String
    private lateinit var mMessage: String
    private var mFailure: Failure? = null

    fun setData(requestKey: String, message: String, failure: Failure?) {
        mRequestKey = requestKey
        mMessage = message
        mFailure = failure
    }

    val requestKey get() = mRequestKey
    val message get() = mMessage
    val failure get() = mFailure

}