package com.klima7.services.common.platform

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.klima7.services.common.data.di.reposModule
import com.klima7.services.common.data.di.sourcesModule
import com.klima7.services.common.di.useCasesModule
import com.klima7.services.common.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

abstract class BaseApp: Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        initPlaces()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@BaseApp)
            modules(commonModules)
            modules(customModules)
        }
    }

    private fun initPlaces() {
        Places.initialize(applicationContext, "AIzaSyBgMDgU7VMT0L35f9TL4LZUB7v3NAS9pTs")
    }

    private val commonModules = listOf(sourcesModule, reposModule, viewModelsModule, useCasesModule)

    protected abstract val customModules: List<Module>
}
