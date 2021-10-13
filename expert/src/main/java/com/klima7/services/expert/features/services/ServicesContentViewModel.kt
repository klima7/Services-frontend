package com.klima7.services.expert.features.services

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.data.repositories.ServicesRepository
import com.klima7.services.common.domain.models.Category
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.domain.utils.Outcome
import com.klima7.services.common.lib.failurable.FailurableViewModel
import kotlinx.coroutines.launch

class ServicesContentViewModel(
    private val servicesRepository: ServicesRepository,
    private val authRepository: AuthRepository,
    private val expertsRepository: ExpertsRepository
): FailurableViewModel() {

    sealed class Event: BaseEvent() {
        data class SetServices(val services: List<CategorizedSelectableServices>): Event()
    }

    override fun refresh() {
        update()
    }

    fun servicesStarted() {
        update()
    }

    private fun update() {
        viewModelScope.launch {
            getCategorizedSelectableServices().foldS({ failure ->
                showFailure(failure)
                Log.i("Hello", "getCategorizedServices in ViewModel failure $failure")
            }, { services ->
                if(services.isEmpty())
                    showFailure(Failure.InternetFailure)
                else {
                    Log.i("Hello", "getCategorizedServices in ViewModel success: $services")
                    sendEvent(Event.SetServices(services))
                    showMain()
                }
            })
        }
    }

    private suspend fun getCategorizedSelectableServices(): Outcome<Failure, List<CategorizedSelectableServices>> {
        return authRepository.getUid().foldS({ failure ->
            return@foldS Outcome.Failure(failure)
        }, { uid ->
            if (uid == null)
                return@foldS Outcome.Failure(Failure.PermissionFailure)
            return@foldS expertsRepository.getExpert(uid).foldS({ failure ->
                Outcome.Failure(failure)
            }, { expert ->
                getAllServicesPart(expert.servicesIds)
            })
        })
    }

    private suspend fun getAllServicesPart(selected: Set<String>): Outcome<Failure, List<CategorizedSelectableServices>> {
        return servicesRepository.getAllServices().foldS({ failure ->
            return@foldS Outcome.Failure(failure)
        }, { services ->
            return@foldS getAllCategoriesPart(selected, services)
        })
    }

    private suspend fun getAllCategoriesPart(selected: Set<String>, services: List<Service>): Outcome<Failure, List<CategorizedSelectableServices>> {
        return servicesRepository.getAllCategories().foldS({ failure ->
            return@foldS Outcome.Failure(failure)
        }, { categories ->
            return@foldS groupServicesPart(selected, services, categories)
        })
    }

    private fun groupServicesPart(selected: Set<String>, services: List<Service>, categories: List<Category>): Outcome<Failure, List<CategorizedSelectableServices>> {
        val result = mutableListOf<CategorizedSelectableServices>()
        categories.forEach { category ->
            val servicesOfCategory = services.filter { service -> service.categoryId == category.id }
            val tmp = servicesOfCategory.map { SelectableService(it, selected.contains(it.id)) }
            result.add(CategorizedSelectableServices(category, tmp))
        }
        return Outcome.Success(result)
    }

    fun servicesSelected(services: List<Service>) {
        Log.i("Hello", "Selected services: $services")
        val servicesIds = services.map { it.id }
        viewModelScope.launch {
            setServicesIds(servicesIds)
        }
    }

    private suspend fun setServicesIds(servicesIds: List<String>) {
        expertsRepository.setServicesIds(servicesIds).foldS({ failure ->
            Log.i("Hello", "Error during setServicesIds: $failure")
        }, {
            Log.i("Hello", "Success during setServicesIds")
        })
    }
}