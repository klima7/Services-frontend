package com.klima7.services.expert.features.area

import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.base.BaseUC
import com.klima7.services.common.utils.None
import com.klima7.services.common.utils.Outcome

class SetCurrentExpertWorkingAreaUC(
    private val expertsRepository: ExpertsRepository
): BaseUC<SetCurrentExpertWorkingAreaUC.Params, None>() {

    override suspend fun execute(params: Params): Outcome<Failure, None> {
        return  expertsRepository.setWorkingArea(params.placeId, params.radius)
    }

    data class Params(val placeId: String, val radius: Int)

}