package com.klima7.services.expert

import com.klima7.services.common.lib.App
import com.klima7.services.expert.di.useCasesExpertModule
import com.klima7.services.expert.di.viewModelsModule

class ExpertApp: App() {
    override val customModules = listOf(viewModelsModule, useCasesExpertModule)
}