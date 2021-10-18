package com.klima7.services.common.ui.profile

import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.utils.BaseUC
import com.klima7.services.common.domain.utils.Outcome

class GetExpertUC(
    private val expertsRepository: ExpertsRepository
): BaseUC<GetExpertUC.Params, Expert>() {

    data class Params(val uid: String)

    override suspend fun execute(params: Params): Outcome<Failure, Expert> {
        return expertsRepository.getExpert(params.uid)
    }
}