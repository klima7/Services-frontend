package com.klima7.services.expert.features.info

import com.klima7.services.common.lib.failurable.FailurableViewModel

class InfoContentViewModel: FailurableViewModel() {

    fun doSomething() {
    }

    override fun refresh() {
        doSomething()
    }

}