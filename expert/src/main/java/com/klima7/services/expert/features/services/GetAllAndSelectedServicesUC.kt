package com.klima7.services.expert.features.services

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.ServicesRepository
import com.klima7.services.common.models.*
import com.klima7.services.expert.usecases.GetCurrentExpertUC

class GetAllAndSelectedServicesUC(
    private val getCurrentExpertUC: GetCurrentExpertUC,
    private val servicesRepository: ServicesRepository,
): BaseUC<None, AllAndSelectedServices>() {

    override suspend fun execute(params: None): Outcome<Failure, AllAndSelectedServices> {
        return getExpertPart()
    }

    private suspend fun getExpertPart(): Outcome<Failure, AllAndSelectedServices> {
        return getCurrentExpertUC.run(None()).foldS({ failure ->
            Outcome.Failure(failure)
        }, { expert ->
            getAllServicesPart(expert.servicesIds)
        })
    }

    private suspend fun getAllServicesPart(selected: Set<String>): Outcome<Failure, AllAndSelectedServices> {
        return servicesRepository.getAllServices().foldS({ failure ->
            Outcome.Failure(failure)
        }, { services ->
            getAllCategoriesPart(selected, services)
        })
    }

    private suspend fun getAllCategoriesPart(selected: Set<String>, services: List<Service>):
            Outcome<Failure, AllAndSelectedServices> {
        return servicesRepository.getAllCategories().foldS({ failure ->
            Outcome.Failure(failure)
        }, { categories ->
            mergePart(selected, services, categories)
        })
    }

    private fun mergePart(selectedServices: Set<String>, services: List<Service>, categories: List<Category>):
            Outcome<Failure, AllAndSelectedServices> {
        val categoriesWithServices = mutableListOf<CategoryWithServices>()
        categories.forEach { category ->
            val servicesOfCategory = services.filter { service -> service.categoryId == category.id }
            val categoryWithServices = CategoryWithServices(category, servicesOfCategory)
            categoriesWithServices.add(categoryWithServices)
        }

        val allAndSelectedServices = AllAndSelectedServices(categoriesWithServices.toSet(), selectedServices)
        return Outcome.Success(allAndSelectedServices)
    }
}