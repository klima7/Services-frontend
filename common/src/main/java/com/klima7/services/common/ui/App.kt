package com.klima7.services.common.ui

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.klima7.services.common.data.di.reposModule
import com.klima7.services.common.data.di.sourcesModule
import com.klima7.services.common.ui.di.viewModelsCommonModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

abstract class App: Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        initPlaces()
    }

    private fun setupKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(commonModules)
            modules(customModules)
        }
    }

    private fun initPlaces() {
        Places.initialize(applicationContext, "AIzaSyBgMDgU7VMT0L35f9TL4LZUB7v3NAS9pTs")
    }

    private val commonModules = listOf(sourcesModule, reposModule, viewModelsCommonModule)

    protected abstract val customModules: List<Module>
}
