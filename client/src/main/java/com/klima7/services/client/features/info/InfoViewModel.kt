package com.klima7.services.client.features.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.klima7.services.client.usecases.GetCurrentClientUC
import com.klima7.services.common.components.views.LoadAreaView
import com.klima7.services.common.core.None
import com.klima7.services.common.extensions.nullifyBlank
import com.klima7.services.common.models.Client
import com.klima7.services.common.models.ClientInfo
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.common.platform.CombinedLiveData

class InfoViewModel(
    private val getCurrentClientUC: GetCurrentClientUC,
    private val setCurrentClientInfoUC: SetCurrentClientInfoUC
): BaseViewModel() {

    val name = MutableLiveData("")
    val phone = MutableLiveData("")

    val loadState = MutableLiveData(LoadAreaView.State.LOAD)
    val loadFailure = MutableLiveData<Failure>()

    val nameError = Transformations.map(name) {
        if (it.nullifyBlank() == null) InfoFormErrors.NameError.NotProvided else null
    }

    val phoneError = Transformations.map(phone) {
        if (it.isEmpty() || it.length == 9) null else InfoFormErrors.PhoneError.TooShort
    }

    val saveButtonEnabled = CombinedLiveData(nameError, phoneError) {
        val (nameError, phoneError) = it
        val enabled = nameError == null && phoneError == null
        enabled
    }

    sealed class Event: BaseEvent() {
        object FinishInfo: Event()
        data class ShowSaveFailure(val failure: Failure): Event()
    }

    fun started() {
        loadContent()
    }

    fun refresh() {
        loadContent()
    }

    fun saveClicked() {
        save()
    }

    fun retrySaveClicked() {
        save()
    }

    private fun loadContent() {
        loadState.value = LoadAreaView.State.LOAD
        getCurrentClientUC.start(
            viewModelScope,
            None(),
            { failure ->
                loadFailure.value = failure
                loadState.value = LoadAreaView.State.FAILURE
            },
            { expert ->
                setFields(expert)
                loadState.value = LoadAreaView.State.MAIN
            }
        )
    }

    private fun setFields(client: Client) {
        name.value = client.info.name ?: ""
        phone.value = client.info.phone ?: ""
    }

    private fun save() {
        loadState.value = LoadAreaView.State.PENDING
        setCurrentClientInfoUC.start(
            viewModelScope,
            SetCurrentClientInfoUC.Params(
                ClientInfo(
                    name.value.nullifyBlank(),
                    phone.value.nullifyBlank(),
                )
            ),
            { failure ->
                sendEvent(Event.ShowSaveFailure(failure))
                loadState.value = LoadAreaView.State.MAIN
            },
            {
                sendEvent(Event.FinishInfo)
                loadState.value = LoadAreaView.State.MAIN
            }
        )
    }

}