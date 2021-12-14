package com.klima7.services.client.features.offers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.klima7.services.client.usecases.GetJobUC
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Job
import com.klima7.services.common.models.OfferStatus
import com.klima7.services.common.models.OfferWithExpert
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.common.platform.CombinedLiveData

class OffersViewModel(
    private val getJobUC: GetJobUC,
    private val getOffersWithExpertForJobUC: GetOffersWithExpertForJobUC,
    private val finishJobUC: FinishJobUC,
): BaseViewModel() {

    sealed class Event: BaseEvent() {
        data class ShowJobDetails(val jobId: String): Event()
        data class ShowFinishJobFailure(val failure: Failure): Event()
    }

    private lateinit var jobId: String
    val wasFinished = MutableLiveData(false)

    val visibleStatuses = MutableLiveData(setOf(OfferStatus.NEW, OfferStatus.CANCELLED,
        OfferStatus.IN_REALIZATION, OfferStatus.DONE))

    val job = MutableLiveData<Job?>(null)
    private val offersWithExperts = MutableLiveData<List<OfferWithExpert>>()
    val visibleOffersWithExperts = CombinedLiveData(offersWithExperts, visibleStatuses) {
        getVisibleOffers()
    }

    val subtitle = job.map { job -> job?.serviceName ?: "" }
    val isJobActive = job.map { it?.active ?: false }
    val offersCount = offersWithExperts.map { it.size }
    val filterVisible = offersCount.map { it > 0 }

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

    fun finishJobConfirmed() {
        finishJob()
    }

    fun retryFinishJob() {
        finishJob()
    }

    fun visibleStatusesChanged(visibleStatuses: Set<OfferStatus>) {
        this.visibleStatuses.value = visibleStatuses
    }

    private fun loadContent() {
        loadJob()
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

    private fun getVisibleOffers(): List<OfferWithExpert> {
        val cOffersWithExperts = offersWithExperts.value ?: return emptyList()
        val cVisibleStatuses = visibleStatuses.value ?: return emptyList()
        return cOffersWithExperts.filter { offerWithExpert ->
            val status = offerWithExpert.offer.status
            cVisibleStatuses.contains(status)
        }
    }

    private fun finishJob() {
        loadState.value = LoadAreaView.State.PENDING
        finishJobUC.start(
            viewModelScope,
            FinishJobUC.Params(jobId),
            { failure ->
                loadState.value = LoadAreaView.State.MAIN
                sendEvent(Event.ShowFinishJobFailure(failure))
            }, {
                loadState.value = LoadAreaView.State.MAIN
                wasFinished.value = true
                refresh()
            }
        )
    }

}