package com.klima7.services.expert.features.setup

import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.utils.Outcome

class GetCurrentExpertSetupStateUC(
    private val authRepository: AuthRepository,
    private val expertsRepository: ExpertsRepository,
) {

    data class Result(  // TODO: Maybe domain model?
        val infoSetup: Boolean,
        val servicesSetup: Boolean,
        val locationSetup: Boolean,
    )

    suspend fun execute(): Outcome<Failure, Result> {
        return getUidPart()
    }

    private suspend fun getUidPart(): Outcome<Failure, Result> {
        return authRepository.getUid().foldS({ failure ->
            Outcome.Failure(failure)
        }, { uid ->
            if(uid == null) {
                Outcome.Failure(Failure.PermissionFailure)
            }
            else {
                getExpertPart(uid)
            }
        })
    }

    private suspend fun getExpertPart(uid: String): Outcome<Failure, Result> {
        return expertsRepository.getExpert(uid).foldS({ failure ->
            Outcome.Failure(failure)
        }, { expert ->
            if(expert.fromCache) {
                Outcome.Failure(Failure.InternetFailure)
            }
            else {
                verifyExpertPart(expert)
            }
        })
    }

    private fun verifyExpertPart(expert: Expert): Outcome<Failure, Result> {
        val infoConfigured = expert.info.name != null
        val servicesConfigured = expert.servicesIds.isNotEmpty()
        val locationConfigured = expert.area != null
        return Outcome.Success(Result(infoConfigured, servicesConfigured, locationConfigured))
    }

}