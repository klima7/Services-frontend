package com.klima7.services.client.di

import com.klima7.services.client.features.delete.DeleteClientUC
import com.klima7.services.client.features.info.SetCurrentClientInfoUC
import com.klima7.services.client.features.setup.GetCurrentClientSetupStateUC
import com.klima7.services.client.features.splash.GetCurrentClientStateUC
import com.klima7.services.client.usecases.GetCurrentClientUC
import org.koin.dsl.module

val useCasesModule = module {

    single { GetCurrentClientStateUC(get(), get()) }
    single { GetCurrentClientUC(get(), get()) }
    single { GetCurrentClientSetupStateUC(get()) }
    single { SetCurrentClientInfoUC(get()) }
    single { DeleteClientUC(get(), get()) }

}
