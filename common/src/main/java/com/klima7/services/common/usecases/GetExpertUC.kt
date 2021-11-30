package com.klima7.services.common.usecases

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.models.Expert

class GetExpertUC(
    private val expertsRepository: ExpertsRepository
): BaseUC<GetExpertUC.Params, Expert>() {

    data class Params(val uid: String)

    override suspend fun execute(params: Params) = expertsRepository.getExpert(params.uid)
}