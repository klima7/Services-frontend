package com.klima7.services.common.components.yesnodialog

import androidx.lifecycle.ViewModel

class YesNoDialogViewModel: ViewModel() {

    private lateinit var mRequestKey: String
    private lateinit var mMessage: String

    fun setData(requestKey: String, message: String) {
        mRequestKey = requestKey
        mMessage = message
    }

    val requestKey get() = mRequestKey
    val message get() = mMessage

}