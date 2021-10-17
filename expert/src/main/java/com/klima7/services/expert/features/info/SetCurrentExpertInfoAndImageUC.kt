package com.klima7.services.expert.features.info

import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.domain.models.ExpertInfo
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.utils.BaseUC
import com.klima7.services.common.domain.utils.None
import com.klima7.services.common.domain.utils.Outcome

class SetCurrentExpertInfoAndImageUC(
    private val expertsRepository: ExpertsRepository
): BaseUC<SetCurrentExpertInfoAndImageUC.Params, None>() {

    override suspend fun execute(params: Params): Outcome<Failure, None> {
        val infoOutcome = expertsRepository.setExpertInfo(params.info)
        if(infoOutcome.isFailure) {
            return infoOutcome
        }
        return if(params.profileImageUrl != null) {
            expertsRepository.setProfileImage(params.profileImageUrl)
        } else {
            Outcome.Success(None())
        }
    }

    data class Params(val info: ExpertInfo, val profileImageUrl: String?)

}