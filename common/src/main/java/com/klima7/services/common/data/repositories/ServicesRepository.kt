package com.klima7.services.common.data.repositories

import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.FirebaseSource
import com.klima7.services.common.models.Category
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Service

class ServicesRepository(
    private val firebase: FirebaseSource,
) {

    suspend fun getAllServices(): Outcome<Failure, List<Service>> {
        return firebase.servicesDao.getAllServices()
    }

    suspend fun getAllCategories(): Outcome<Failure, List<Category>> {
        return firebase.servicesDao.getAllCategories()
    }

    suspend fun getService(serviceId: String): Outcome<Failure, Service> {
        return firebase.servicesDao.getService(serviceId)
    }

    suspend fun getCategory(categoryId: String): Outcome<Failure, Category> {
        return firebase.servicesDao.getCategory(categoryId)
    }

    suspend fun getServicesFromCategory(categoryId: String): Outcome<Failure, List<Service>> {
        return firebase.servicesDao.getServicesFromCategory(categoryId)
    }

}