package com.klima7.services.common.data.sources.firebase

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.klima7.services.common.BuildConfig
import com.klima7.services.common.data.sources.firebase.dao.*

const val EMULATE = BuildConfig.EMULATE
const val EMULATOR_HOST = "10.0.2.2"

class FirebaseSource {

    private val functions = Firebase.functions
    private val firestore = Firebase.firestore
    private val auth = Firebase.auth
    private val storage = Firebase.storage

    private fun setupEmulator() {
        functions.useEmulator(EMULATOR_HOST, 5001)
        firestore.useEmulator(EMULATOR_HOST, 8080)
        auth.useEmulator(EMULATOR_HOST, 9099)
        storage.useEmulator(EMULATOR_HOST, 9199)
    }

    init {
        if(EMULATE)
            setupEmulator()
    }

    val authDao = AuthDao(auth);
    val clientsDao = ClientsDao(firestore, functions)
    val expertsDao = ExpertsDao(auth, firestore, functions, storage)
    val jobsDao = JobsDao(firestore, functions)
    val jobsStatusDao = JobsStatusDao(functions)
    val messagesDao = MessagesDao(auth, firestore, storage)
    val offersDao = OffersDao(firestore, functions)
    val ratingsDao = RatingsDao(firestore, functions)
    val servicesDao = ServicesDao(firestore)

}