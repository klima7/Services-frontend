package com.klima7.services.expert.features.services

import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.data.repositories.ServicesRepository
import com.klima7.services.common.domain.models.Category
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.domain.utils.BaseUC
import com.klima7.services.common.domain.utils.None
import com.klima7.services.common.domain.utils.Outcome
import com.klima7.services.expert.common.domain.usecases.GetCurrentExpertUC

class GetCategorisedAndMarkedServices(
    private val servicesRepository: ServicesRepository,
    private val getCurrentExpertUC: GetCurrentExpertUC,
    private val expertsRepository: ExpertsRepository,
): BaseUC<None, List<CategorizedSelectableServices>>() {

    override suspend fun execute(params: None): Outcome<Failure, List<CategorizedSelectableServices>> {
        return getExpertPart()
    }

    private suspend fun getExpertPart(): Outcome<Failure, List<CategorizedSelectableServices>> {
        return getCurrentExpertUC.execute(None()).foldS({ failure ->
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