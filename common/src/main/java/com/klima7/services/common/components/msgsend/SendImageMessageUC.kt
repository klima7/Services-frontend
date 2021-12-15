package com.klima7.services.common.components.msgsend

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.MessagesRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.Role
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

class SendImageMessageUC(
    private val messagesRepository: MessagesRepository
): BaseUC<SendImageMessageUC.Params, None>() {

    data class Params(val offerId: String, val sender: Role, val imagePath: String)

    @OptIn(ExperimentalTime::class)
    override suspend fun execute(params: Params): Outcome<Failure, None> {
        return try {
            withTimeout(Duration.seconds(5)) {
                messagesRepository.sendImageMessage(params.offerId, params.sender, params.imagePath)
            }
        } catch (e: Exception) {
            Outcome.Failure(Failure.InternetFailure)
        }
    }
}