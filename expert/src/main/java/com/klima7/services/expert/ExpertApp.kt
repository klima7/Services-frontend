package com.klima7.services.expert

import com.klima7.services.common.App
import com.klima7.services.expert.di.viewModelsModule

class ExpertApp: App() {
    override val customModules = listOf(viewModelsModule)
}