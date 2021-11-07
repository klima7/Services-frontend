package com.klima7.services.client.features.offers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.client.usecases.GetJobUC
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Job
import com.klima7.services.common.platform.BaseViewModel

class OffersViewModel(
    private val getJobUC: GetJobUC,
    private val getOffersWithExpertForJobUC: GetOffersWithExpertForJobUC
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        data class ShowJobDetails(val jobId: String): Event()
    }

    private lateinit var jobId: String
    val job = MutableLiveData<Job>()
    val subtitle = job.map { job -> job.serviceName }
    val loadState = MutableLiveData(LoadAreaView.State.MAIN)

    fun start(jobId: String) {
        this.jobId = jobId
        loadContent()
    }

    fun refresh() {
        loadContent()
    }

    fun showDetailsClicked() {
        sendEvent(Event.ShowJobDetails(jobId))
    }

    private fun loadContent() {
        getJobUC.start(
            viewModelScope,
            GetJobUC.Params(jobId),
            { failure ->
                Log.i("Hello", "Failure: $failure")
            },
            { job ->
                this.job.value = job
            }
        )
    }

}