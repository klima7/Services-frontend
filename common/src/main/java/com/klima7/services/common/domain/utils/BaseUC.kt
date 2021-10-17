package com.klima7.services.common.domain.utils

import android.util.Log
import com.klima7.services.common.domain.models.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseUC<P, R> {

    val UC_NAME: String = this::class.java.simpleName
    val TAG: String = "usecase-$UC_NAME"

    protected abstract suspend fun execute(params: P): Outcome<Failure, R>

    suspend fun run(params: P): Outcome<Failure, R> {
        Log.v(TAG, "Executing $UC_NAME UC with parameters: $params")
        val outcome = execute(params)
        outcome.fold({ failure ->
            Log.v(TAG, "UC $UC_NAME failed with failure: $failure")
        }, { result ->
            Log.v(TAG, "UC $UC_NAME finished successfully with result: $result")
        })
        return outcome
    }

    fun start(scope: CoroutineScope, params: P, onFailure: (Failure)->Unit, onSuccess: (R)->Unit) {
        scope.launch {
            run(params).foldS({ failure ->
                onFailure(failure)
            }, { result ->
                onSuccess(result)
            })
        }
    }

}
