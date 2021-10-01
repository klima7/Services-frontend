package com.klima7.services.common.di

import com.klima7.services.common.repo.ExpertsRepository
import org.koin.dsl.module

val reposModule = module {

    single { ExpertsRepository(get()) }

}
