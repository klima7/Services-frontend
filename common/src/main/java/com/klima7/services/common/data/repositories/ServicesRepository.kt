package com.klima7.services.common.data.repositories

import com.klima7.services.common.domain.models.Category
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.domain.util.Outcome

class ServicesRepository {

    suspend fun getAllServices(): Outcome<Failure, List<Service>> {
        return Outcome.Success(listOf())
    }

    suspend fun getAllCategories(): Outcome<Failure, List<Category>> {
        return Outcome.Success(listOf())
    }

    suspend fun getService(serviceId: String): Outcome<Failure, Service> {
        return Outcome.Failure(Failure.NotFoundFailure)
    }

    suspend fun getCategory(categoryId: String): Outcome<Failure, Category> {
        return Outcome.Failure(Failure.NotFoundFailure)
    }

}