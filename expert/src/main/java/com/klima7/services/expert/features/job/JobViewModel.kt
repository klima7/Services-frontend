package com.klima7.services.expert.features.job

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.models.Job
import com.klima7.services.common.platform.BaseLoadViewModel
import com.klima7.services.common.usecases.GetJobUC

class JobViewModel(
    private val getJobUC: GetJobUC
): BaseLoadViewModel() {

    private lateinit var jobId: String
    val job = MutableLiveData<Job>()

    fun start(jobId: String) {
        this.jobId = jobId
        loadContent()
    }

    override fun refresh() {
        loadContent()
    }

    private fun loadContent() {
        showLoading()
        getJobUC.start(
            viewModelScope,
            GetJobUC.Params(jobId),
            { failure ->
                showFailure(failure)
            },
            { job ->
                this.job.value = job
                showMain()
            }
        )
    }

}