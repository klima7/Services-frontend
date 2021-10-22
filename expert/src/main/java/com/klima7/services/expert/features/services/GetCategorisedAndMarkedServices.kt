package com.klima7.services.expert.features.services

import com.klima7.services.common.data.repositories.ServicesRepository
import com.klima7.services.common.models.Category
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Service
import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.expert.usecases.GetCurrentExpertUC

class GetCategorisedAndMarkedServices(
    private val servicesRepository: ServicesRepository,
    private val getCurrentExpertUC: GetCurrentExpertUC,
): BaseUC<None, List<CategorizedSelectableServices>>() {

    override suspend fun execute(params: None): Outcome<Failure, List<CategorizedSelectableServices>> {
        return getExpertPart()
    }

    private suspend fun getExpertPart(): Outcome<Failure, List<CategorizedSelectableServices>> {
        return getCurrentExpertUC.run(None()).foldS({ failure ->
            Outcome.Failure(failure)
        }, { expert ->
            getAllServicesPart(expert.servicesIds)
        })
    }

    private suspend fun getAllServicesPart(selected: Set<String>): Outcome<Failure, List<CategorizedSelectableServices>> {
        return servicesRepository.getAllServices().foldS({ failure ->
            Outcome.Failure(failure)
        }, { services ->
            getAllCategoriesPart(selected, services)
        })
    }

    private suspend fun getAllCategoriesPart(selected: Set<String>, services: List<Service>): Outcome<Failure, List<CategorizedSelectableServices>> {
        return servicesRepository.getAllCategories().foldS({ failure ->
            Outcome.Failure(failure)
        }, { categories ->
            groupServicesPart(selected, services, categories)
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
}