package com.klima7.services.expert.usecases

import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.models.Expert
import com.klima7.services.common.models.Failure
import com.klima7.services.common.base.BaseUC
import com.klima7.services.common.utils.None
import com.klima7.services.common.utils.Outcome

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