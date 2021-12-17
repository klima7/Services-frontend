package com.klima7.services.client.messaging

import android.content.Context
import androidx.work.*
import com.klima7.services.common.core.None
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

abstract class RefreshTokenWorker(
    protected val context: Context,
    workerParams: WorkerParameters
):
    Worker(context, workerParams) {

    private val updateTokenUC = UpdateTokenUC()

    @OptIn(DelicateCoroutinesApi::class)
    override fun doWork(): Result {
        GlobalScope.launch {
            updateTokenUC.run(None())
        }
        return Result.success()
    }

    companion object {
        fun schedule(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val work = PeriodicWorkRequest.Builder(
                RefreshTokenWorker::class.java,
                PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
                TimeUnit.MILLISECONDS
            )
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork("refresh token", ExistingPeriodicWorkPolicy.KEEP, work);
        }
    }

}