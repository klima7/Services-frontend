package com.klima7.services.common.core

import android.util.Log
import com.klima7.services.common.models.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class BaseUC<P, R>(
    private val context: CoroutineContext = Dispatchers.Main
) {

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
