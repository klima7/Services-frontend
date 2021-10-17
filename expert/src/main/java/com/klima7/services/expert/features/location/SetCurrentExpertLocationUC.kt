package com.klima7.services.expert.features.location

import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.utils.BaseUC
import com.klima7.services.common.domain.utils.None
import com.klima7.services.common.domain.utils.Outcome

class SetCurrentExpertLocationUC(
    private val expertsRepository: ExpertsRepository
): BaseUC<SetCurrentExpertLocationUC.Params, None>() {

    override suspend fun execute(params: Params): Outcome<Failure, None> {
        return  expertsRepository.setWorkingArea(params.placeId, params.radius)
    }

    data class Params(val placeId: String, val radius: Int)

}