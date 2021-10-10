package com.klima7.services.expert.features.services

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.klima7.services.common.data.repositories.ServicesRepository
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
        viewModelScope.launch {
            servicesRepository.getAllServices().foldS({ failure ->
                Log.i("Hello", "getAllServices in ViewModel failure $failure")
            }, { services ->
                Log.i("Hello", "getAllServices in ViewModel success: $services")
            })
        }

//        viewModelScope.launch {
//            servicesRepository.getAllCategories().foldS({ failure ->
//                Log.i("Hello", "getAllCategories in ViewModel failure $failure")
//            }, { services ->
//                Log.i("Hello", "getAllCategories in ViewModel success: $services")
//            })
//        }


        viewModelScope.launch {
            servicesRepository.getCategory("Builder").foldS({ failure ->
                Log.i("Hello", "getCategory in ViewModel failure $failure")
            }, { services ->
                Log.i("Hello", "getCategory in ViewModel success: $services")
            })
        }

        viewModelScope.launch {
            servicesRepository.getService("service1").foldS({ failure ->
                Log.i("Hello", "getService in ViewModel failure $failure")
            }, { services ->
                Log.i("Hello", "getService in ViewModel success: $services")
            })
        }
    }

}