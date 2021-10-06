package com.klima7.services.expert.features.services

import com.klima7.services.common.lib.failurable.FailurableViewModel

class ServicesContentViewModel: FailurableViewModel() {

    fun doSomething() {
    }

    fun saveClicked() {

    }

    override fun refresh() {
        doSomething()
    }

}