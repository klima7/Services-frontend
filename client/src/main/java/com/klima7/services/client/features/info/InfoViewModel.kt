package com.klima7.services.client.features.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.klima7.services.client.usecases.GetCurrentClientUC
import com.klima7.services.common.core.None
import com.klima7.services.common.extensions.nullifyBlank
import com.klima7.services.common.models.Client
import com.klima7.services.common.models.ClientInfo
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseLoadViewModel
import com.klima7.services.common.platform.CombinedLiveData

class InfoViewModel(
    private val getCurrentClientUC: GetCurrentClientUC,
    private val setCurrentClientInfoUC: SetCurrentClientInfoUC
): BaseLoadViewModel() {

    val name = MutableLiveData("")
    val phone = MutableLiveData("")

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

    override fun refresh() {
        loadContent()
    }

    fun saveClicked() {
        save()
    }

    fun retrySaveClicked() {
        save()
    }

    private fun loadContent() {
        showLoading()
        getCurrentClientUC.start(
            viewModelScope,
            None(),
            { failure ->
                showFailure(failure)
            },
            { expert ->
                setFields(expert)
                showMain()
            }
        )
    }

    private fun setFields(client: Client) {
        name.value = client.info.name ?: ""
        phone.value = client.info.phone ?: ""
    }

    private fun save() {
        showPending()
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
                showMain()
            },
            {
                sendEvent(Event.FinishInfo)
                showMain()
            }
        )
    }

}