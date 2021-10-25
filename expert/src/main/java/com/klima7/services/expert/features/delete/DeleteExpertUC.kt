package com.klima7.services.expert.features.delete

import com.klima7.services.common.components.delete.BaseDeleteUserUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.AuthRepository
import com.klima7.services.common.data.repositories.ExpertsRepository
import com.klima7.services.common.models.Failure
import kotlinx.coroutines.delay

class DeleteExpertUC(
    private val expertsRepository: ExpertsRepository,
    private val authRepository: AuthRepository
): BaseDeleteUserUC() {

    override suspend fun execute(params: None): Outcome<Failure, None> {
        delay(3000)
        return Outcome.Failure(Failure.InternetFailure)
    }

}