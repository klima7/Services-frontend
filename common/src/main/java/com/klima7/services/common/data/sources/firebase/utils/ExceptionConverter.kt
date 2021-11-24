package com.klima7.services.common.data.sources.firebase.utils

import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.storage.StorageException
import com.klima7.services.common.models.Failure
import java.net.ConnectException

fun Exception.toDomain(): Failure {
    return handleException(this)
}

private fun handleException(e: Exception): Failure {
    return when(e) {
        is FirebaseException -> handleFirebaseException(e)
        else -> Failure.UnknownFailure
    }
}

private fun handleFirebaseException(e: FirebaseException): Failure {
    return when(e) {
        is StorageException -> handleStorageException(e)
        is FirebaseFirestoreException -> handleFirestoreException(e)
        is FirebaseFunctionsException -> handleFunctionsException(e)
        else -> Failure.UnknownFailure
    }
}

private fun handleStorageException(e: StorageException): Failure {
    return when(e.errorCode) {
        StorageException.ERROR_QUOTA_EXCEEDED -> Failure.ServerFailure
        StorageException.ERROR_NOT_AUTHENTICATED -> Failure.PermissionFailure
        StorageException.ERROR_NOT_AUTHORIZED -> Failure.PermissionFailure
        StorageException.ERROR_RETRY_LIMIT_EXCEEDED -> Failure.InternetFailure
        StorageException.ERROR_UNKNOWN -> Failure.UnknownFailure
        else -> Failure.UnknownFailure
    }
}

private fun handleFirestoreException(e: FirebaseFirestoreException): Failure {
    return when(e.code) {
        FirebaseFirestoreException.Code.INTERNAL -> Failure.ServerFailure
        FirebaseFirestoreException.Code.UNIMPLEMENTED -> Failure.ServerFailure
        FirebaseFirestoreException.Code.RESOURCE_EXHAUSTED -> Failure.ServerFailure
        FirebaseFirestoreException.Code.UNAUTHENTICATED -> Failure.PermissionFailure
        FirebaseFirestoreException.Code.PERMISSION_DENIED -> Failure.PermissionFailure
        FirebaseFirestoreException.Code.UNAVAILABLE -> Failure.InternetFailure
        else -> Failure.UnknownFailure
    }
}

private fun handleFunctionsException(e: FirebaseFunctionsException): Failure {
    return when(e.code) {
        FirebaseFunctionsException.Code.INTERNAL -> handleFunctionsInternalException(e)
        FirebaseFunctionsException.Code.UNIMPLEMENTED -> Failure.ServerFailure
        FirebaseFunctionsException.Code.RESOURCE_EXHAUSTED -> Failure.ServerFailure
        FirebaseFunctionsException.Code.PERMISSION_DENIED -> Failure.PermissionFailure
        FirebaseFunctionsException.Code.UNAVAILABLE -> Failure.InternetFailure
        FirebaseFunctionsException.Code.UNKNOWN -> Failure.UnknownFailure
        FirebaseFunctionsException.Code.UNAUTHENTICATED -> Failure.PermissionFailure
        else -> Failure.UnknownFailure
    }
}

private fun handleFunctionsInternalException(e: FirebaseFunctionsException): Failure {
    return when(e.cause) {
        is ConnectException -> Failure.InternetFailure
        else -> Failure.ServerFailure
    }
}