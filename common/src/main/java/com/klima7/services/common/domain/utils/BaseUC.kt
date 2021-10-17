package com.klima7.services.common.domain.utils

import com.klima7.services.common.domain.models.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseUC<P, R> {

    abstract suspend fun execute(params: P): Outcome<Failure, R>

    fun start(scope: CoroutineScope, params: P, onFailure: (Failure)->Unit, onSuccess: (R)->Unit) {
        scope.launch {
            execute(params).foldS({ failure ->
                onFailure(failure)
            }, { result ->
                onSuccess(result)
            })
        }
    }

    class NoParams()
    class NoResult()

}
