package com.klima7.services.common.models

sealed class Failure {
    object InternetFailure : Failure()
    object ServerFailure : Failure()
    object ExpertNotFoundFailure : Failure()
    object SpecificFailure : Failure()
}