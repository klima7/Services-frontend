package com.klima7.services.common

import android.app.Application
import com.klima7.services.common.di.dataSourcesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

abstract class App: Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(commonModules)
            modules(customModules)
        }
    }

    private val commonModules = listOf(dataSourcesModule)

    protected abstract val customModules: List<Module>
}
