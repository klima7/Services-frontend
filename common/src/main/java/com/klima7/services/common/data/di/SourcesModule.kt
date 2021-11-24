package com.klima7.services.common.data.di

import androidx.room.Room
import com.klima7.services.common.data.firebase.FirebaseSource
import com.klima7.services.common.data.room.RoomSource
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val sourcesModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            RoomSource::class.java, "database"
        ).build()
    }

    single {
        FirebaseSource()
    }

}