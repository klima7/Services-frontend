package com.klima7.services.client.usecases

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.models.Expert
import com.klima7.services.common.models.Failure

class GetExpertUC(
    private val expertsRepository: ExpertsRepository
): BaseUC<GetExpertUC.Params, Expert>() {

    data class Params(val uid: String)

    override suspend fun execute(params: Params): Outcome<Failure, Expert> {
        return expertsRepository.getExpert(params.uid)
    }
}