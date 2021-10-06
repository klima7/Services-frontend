package com.klima7.services.common.domain.models

sealed class Failure {
    object InternetFailure : Failure()
    object ServerFailure : Failure()
    object UnknownFailure : Failure()
    object PermissionFailure : Failure()
    object NotFoundFailure : Failure()
}