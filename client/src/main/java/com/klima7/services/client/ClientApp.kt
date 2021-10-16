package com.klima7.services.client

import com.klima7.services.common.ui.App
import org.koin.core.module.Module

class ClientApp: App() {
    override val customModules = listOf<Module>()
}