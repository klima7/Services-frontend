package com.klima7.services.common.data.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.klima7.services.common.BuildConfig
import org.koin.dsl.module

const val EMULATE = BuildConfig.EMULATE
const val EMULATOR_HOST = "10.0.2.2"

val sourcesModule = module {

    single {
        if(EMULATE)
            Firebase.functions.useEmulator(EMULATOR_HOST, 5001)
        Firebase.functions
    }

    single {
        if(EMULATE)
            Firebase.firestore.useEmulator(EMULATOR_HOST, 8080)
        Firebase.firestore
    }

    single {
        if(EMULATE)
            Firebase.auth.useEmulator(EMULATOR_HOST, 9099)
        Firebase.auth
    }

    single {
        if(EMULATE)
            Firebase.storage.useEmulator(EMULATOR_HOST, 9199)
        Firebase.storage
    }

}