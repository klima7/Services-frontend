package com.klima7.services.client

import com.klima7.services.common.base.BaseApp
import org.koin.core.module.Module

class ClientApp: BaseApp() {
    override val customModules = listOf<Module>()
}