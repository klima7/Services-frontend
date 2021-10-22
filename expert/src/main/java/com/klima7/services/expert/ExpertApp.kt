package com.klima7.services.expert

import com.klima7.services.common.base.BaseApp
import com.klima7.services.expert.di.useCasesModule
import com.klima7.services.expert.di.viewModelsModule

class ExpertApp: BaseApp() {
    override val customModules = listOf(viewModelsModule, useCasesModule)
}