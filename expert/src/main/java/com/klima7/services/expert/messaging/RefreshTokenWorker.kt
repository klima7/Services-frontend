package com.klima7.services.expert.messaging

import android.content.Context
import androidx.work.*
import com.klima7.services.common.core.None
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class RefreshTokenWorker(
    context: Context,
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
                30,
                TimeUnit.DAYS
            )
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork("refresh token", ExistingPeriodicWorkPolicy.KEEP, work)
        }
    }

}