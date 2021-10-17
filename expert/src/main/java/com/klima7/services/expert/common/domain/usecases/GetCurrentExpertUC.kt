package com.klima7.services.expert.common.domain.usecases

import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.utils.BaseUC
import com.klima7.services.common.domain.utils.None
import com.klima7.services.common.domain.utils.Outcome

class GetCurrentExpertUC(
    private val authRepository: AuthRepository,
    private val expertsRepository: ExpertsRepository
) : BaseUC<None, Expert>(){

    override suspend fun execute(params: None): Outcome<Failure, Expert> {
        return getUidPart()
    }

    private suspend fun getUidPart(): Outcome<Failure, Expert> {
        return authRepository.getUid().foldS({ failure ->
            Outcome.Failure(failure)
        }, { uid ->
            if(uid == null)
                Outcome.Failure(Failure.PermissionFailure)
            else
                getExpertPart(uid)
        })
    }

    private suspend fun getExpertPart(uid: String): Outcome<Failure, Expert> {
        return expertsRepository.getExpert(uid).foldS({ failure ->
            Outcome.Failure(failure)
        }, { expert ->
            Outcome.Success(expert)
        })
    }

}