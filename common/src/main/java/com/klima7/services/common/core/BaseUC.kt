package com.klima7.services.common.core

import com.klima7.services.common.models.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class BaseUC<P, R>(
    protected val context: CoroutineContext = Dispatchers.Main
) {
    protected abstract suspend fun execute(params: P): Outcome<Failure, R>

    suspend fun run(params: P): Outcome<Failure, R> {
        val name = this::class.java.simpleName
        Timber.v("Executing $name UC with parameters: $params")
        val outcome = execute(params)
        outcome.fold({ failure ->
            Timber.v("UC $name failed with failure: $failure")
        }, { result ->
            Timber.v("UC $name finished successfully with result: $result")
        })
        return outcome
    }

    fun start(scope: CoroutineScope, params: P, onFailure: suspend (Failure)->Unit,
              onSuccess: suspend (R)->Unit) {
        scope.launch(context) {
            run(params).foldS({ failure ->
                withContext(Dispatchers.Main) {
                    onFailure(failure)
                }
            }, { result ->
                withContext(Dispatchers.Main) {
                    onSuccess(result)
                }
            })
        }
    }

}
