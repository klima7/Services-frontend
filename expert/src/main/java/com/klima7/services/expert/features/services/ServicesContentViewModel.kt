package com.klima7.services.expert.features.services

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.data.repositories.ServicesRepository
import com.klima7.services.common.domain.models.Category
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.domain.util.Outcome
import com.klima7.services.common.lib.failurable.FailurableViewModel
import kotlinx.coroutines.launch

class ServicesContentViewModel(
    private val servicesRepository: ServicesRepository
): FailurableViewModel() {

    fun doSomething() {

    }

    fun saveClicked() {

    }

    override fun refresh() {
        doSomething()
    }

    fun servicesStarted() {
//        viewModelScope.launch {
//            servicesRepository.getAllServices().foldS({ failure ->
//                Log.i("Hello", "getAllServices in ViewModel failure $failure")
//            }, { services ->
//                Log.i("Hello", "getAllServices in ViewModel success: $services")
//            })
//        }

//        viewModelScope.launch {
//            servicesRepository.getAllCategories().foldS({ failure ->
//                Log.i("Hello", "getAllCategories in ViewModel failure $failure")
//            }, { services ->
//                Log.i("Hello", "getAllCategories in ViewModel success: $services")
//            })
//        }


//        viewModelScope.launch {
//            servicesRepository.getCategory("Builder").foldS({ failure ->
//                Log.i("Hello", "getCategory in ViewModel failure $failure")
//            }, { services ->
//                Log.i("Hello", "getCategory in ViewModel success: $services")
//            })
//        }

//        viewModelScope.launch {
//            servicesRepository.getService("service1").foldS({ failure ->
//                Log.i("Hello", "getService in ViewModel failure $failure")
//            }, { services ->
//                Log.i("Hello", "getService in ViewModel success: $services")
//            })
//        }

        viewModelScope.launch {
            getCategorizedServices().foldS({ failure ->
                Log.i("Hello", "getCategorizedServices in ViewModel failure $failure")
            }, { services ->
                Log.i("Hello", "getCategorizedServices in ViewModel success: $services")
            })
        }
    }

    private suspend fun getCategorizedServices(): Outcome<Failure, List<CategorizedServices>> = getServicesPart()

    private suspend fun getServicesPart(): Outcome<Failure, List<CategorizedServices>> {
        return servicesRepository.getAllServices().foldS({ failure ->
            return@foldS Outcome.Failure(failure)
        }, { services ->
            return@foldS getCategoriesPart(services)
        })
    }

    private suspend fun getCategoriesPart(services: List<Service>): Outcome<Failure, List<CategorizedServices>> {
        return servicesRepository.getAllCategories().foldS({ failure ->
            return@foldS Outcome.Failure(failure)
        }, { categories ->
            return@foldS groupServicesPart(services, categories)
        })
    }

    private fun groupServicesPart(services: List<Service>, categories: List<Category>): Outcome<Failure, List<CategorizedServices>> {
        val result = mutableListOf<CategorizedServices>()
        categories.forEach { category ->
            val servicesOfCategory = services.filter { service -> service.categoryId == category.id }
            result.add(CategorizedServices(category, servicesOfCategory))
        }
        return Outcome.Success(result)
    }

}