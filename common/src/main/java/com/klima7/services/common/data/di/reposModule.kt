package com.klima7.services.common.data.di

import com.klima7.services.common.data.repo.ExpertsRepository
import org.koin.dsl.module

val reposModule = module {

    single { ExpertsRepository(get()) }

}
