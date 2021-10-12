package com.klima7.services.expert.usecases

import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.utils.Outcome

class GetCurrentExpertUC(
    private val authRepository: AuthRepository,
    private val expertsRepository: ExpertsRepository
) {

    suspend fun execute(): Outcome<Failure, Expert> {
        return getUidPart()
    }

    suspend fun getUidPart(): Outcome<Failure, Expert> {
        return authRepository.getUid().foldS({ failure ->
            Outcome.Failure(failure)
        }, { uid ->
            if(uid == null)
                Outcome.Failure(Failure.PermissionFailure)
            else
                getExpertPart(uid)
        })
    }

    suspend fun getExpertPart(uid: String): Outcome<Failure, Expert> {
        return expertsRepository.getExpert(uid).foldS({ failure ->
            Outcome.Failure(failure)
        }, { expert ->
            Outcome.Success(expert)
        })
    }

}