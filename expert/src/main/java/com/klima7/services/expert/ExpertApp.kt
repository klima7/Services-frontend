package com.klima7.services.expert

import com.klima7.services.common.platform.BaseApp
import com.klima7.services.expert.di.useCasesModule
import com.klima7.services.expert.di.viewModelsModule

class ExpertApp: BaseApp() {
    override val customModules = listOf(viewModelsModule, useCasesModule)
}