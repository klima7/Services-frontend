package com.klima7.services.common.data.repositories

import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.FirebaseSource
import com.klima7.services.common.models.Failure

class AuthRepository(
    private val firebase: FirebaseSource,
) {

    fun getUid(): Outcome<Failure, String?> {
        return firebase.authDao.getUid()
    }

    fun signOut(): Outcome<Failure, None> {
        return firebase.authDao.signOut()
    }

}