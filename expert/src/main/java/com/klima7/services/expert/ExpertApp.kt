package com.klima7.services.expert

import com.klima7.services.common.ui.App
import com.klima7.services.expert.di.useCasesExpertModule
import com.klima7.services.expert.di.viewModelsExpertModule

class ExpertApp: App() {
    override val customModules = listOf(viewModelsExpertModule, useCasesExpertModule)
}