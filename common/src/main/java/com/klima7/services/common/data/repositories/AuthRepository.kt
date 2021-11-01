package com.klima7.services.common.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.models.Failure

class AuthRepository(
    private val auth: FirebaseAuth
) {

    fun getUid(): Outcome<Failure, String?> {
        return try {
            Outcome.Success(auth.currentUser?.uid)
        } catch(e: Exception) {
            Outcome.Failure(Failure.UnknownFailure)
        }
    }

    fun signOut(): Outcome<Failure, None> {
        return try {
            auth.signOut()
            Outcome.Success(None())
        } catch(e: Exception) {
            Outcome.Failure(Failure.UnknownFailure)
        }
    }

}