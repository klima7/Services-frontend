package com.klima7.services.expert.features.jobs.base

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.models.Failure
import kotlinx.coroutines.Dispatchers

abstract class BaseGetJobsIdsUC(
    private val authRepository: AuthRepository,
): BaseUC<None, List<String>>(Dispatchers.IO) {

    override suspend fun execute(params: None): Outcome<Failure, List<String>> {
        return getUidPart()
    }

    private suspend fun getUidPart(): Outcome<Failure, List<String>> {
        return authRepository.getUid().foldS({ failure ->
            Outcome.Failure(failure)
        }, { uid ->
            if(uid == null) {
                return@foldS Outcome.Failure(Failure.PermissionFailure)
            }
            getIdsPart(uid)
        })
    }

    abstract suspend fun getIdsPart(uid: String): Outcome<Failure, List<String>>
}