package com.klima7.services.expert.features.splash

import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.models.UserState
import com.klima7.services.common.domain.utils.BaseUC
import com.klima7.services.common.domain.utils.Outcome

class GetCurrentExpertStateUC(
    private val authRepository: AuthRepository,
    private val expertsRepository: ExpertsRepository
): BaseUC<GetCurrentExpertStateUC.Params, UserState>() {

    class Params

    override suspend fun execute(params: Params): Outcome<Failure, UserState> {
        return getUidPart()
    }

    private suspend fun getUidPart(): Outcome<Failure, UserState> {
        return authRepository.getUid().foldS({ failure ->
            Outcome.Failure(failure)
        }, { uid ->
            if(uid == null) {
                Outcome.Success(UserState.NOT_LOGGED)
            } else {
                getExpertPart(uid)
            }
        })
    }

    private suspend fun getExpertPart(uid: String): Outcome<Failure, UserState> {
        return expertsRepository.getExpert(uid).foldS({ failure ->
            when(failure) {
                Failure.NotFoundFailure -> createExpertPart(uid)
                else -> Outcome.Failure(failure)
            }
        }, { expert ->
            analyseExpertPart(expert)
        })
    }

    private suspend fun createExpertPart(uid: String): Outcome<Failure, UserState> {
        return expertsRepository.createExpertAccount().foldS({ failure ->
            Outcome.Failure(failure)
        }, {
            getExpertPart(uid)
        })
    }

    private fun analyseExpertPart(expert: Expert): Outcome<Failure, UserState> {
        return if(isExpertReady(expert)) {
            Outcome.Success(UserState.READY)
        } else if(!expert.fromCache) {
            Outcome.Success(UserState.NOT_READY)
        } else {
            Outcome.Failure(Failure.InternetFailure)
        }
    }

    private fun isExpertReady(expert: Expert): Boolean {
        return expert.info.name != null && expert.area != null && expert.servicesIds.isNotEmpty()
    }

}