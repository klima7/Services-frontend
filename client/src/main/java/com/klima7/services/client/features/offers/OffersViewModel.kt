package com.klima7.services.client.features.offers

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.client.usecases.GetJobUC
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Job
import com.klima7.services.common.models.OfferWithExpert
import com.klima7.services.common.platform.BaseViewModel

class OffersViewModel(
    private val getJobUC: GetJobUC,
    private val getOffersWithExpertForJobUC: GetOffersWithExpertForJobUC
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        data class ShowJobDetails(val jobId: String): Event()
    }

    private lateinit var jobId: String
    val job = MutableLiveData<Job?>(null)
    val offersWithExperts = MutableLiveData<List<OfferWithExpert>>()
    val subtitle = job.map { job -> job?.serviceName ?: "" }

    val loadState = MutableLiveData(LoadAreaView.State.MAIN)
    val loadFailure = MutableLiveData<Failure>()

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
        if(job.value == null) {
            loadJob()
        }
        loadOffers()
    }

    private fun loadJob() {
        getJobUC.start(
            viewModelScope,
            GetJobUC.Params(jobId),
            {},
            { job ->
                this.job.value = job
            }
        )
    }

    private fun loadOffers() {
        loadState.value = LoadAreaView.State.LOAD
        getOffersWithExpertForJobUC.start(
            viewModelScope,
            GetOffersWithExpertForJobUC.Params(jobId),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            { offersWithExperts ->
                loadState.value = LoadAreaView.State.MAIN
                this.offersWithExperts.value = offersWithExperts
            }
        )
    }

}