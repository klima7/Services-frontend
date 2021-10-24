package com.klima7.services.client

import com.klima7.services.client.di.useCasesModule
import com.klima7.services.client.di.viewModelsModule
import com.klima7.services.common.platform.BaseApp
import org.koin.core.module.Module

class ClientApp: BaseApp() {
    override val customModules = listOf<Module>(useCasesModule, viewModelsModule)
}