package com.klima7.services.expert.features.job

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Job
import com.klima7.services.common.platform.BaseLoadViewModel
import com.klima7.services.common.usecases.GetJobUC
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JobViewModel(
    private val getJobUC: GetJobUC
): BaseLoadViewModel() {

    private lateinit var jobId: String
    val job = MutableLiveData<Job>()

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val failure = MutableLiveData<Failure>()

    fun start(jobId: String) {
        this.jobId = jobId
        loadContent()

        viewModelScope.launch {
            delay(3000)
            failure.value = Failure.InternetFailure
            loadState.value = LoadAreaView.State.FAILURE
            delay(3000)
            loadState.value = LoadAreaView.State.MAIN
            delay(3000)
            loadState.value = LoadAreaView.State.PENDING
            delay(3000)
            loadState.value = LoadAreaView.State.MAIN
        }
    }

    override fun refresh() {
        Log.i("Hello", "Refreshing in VM")
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