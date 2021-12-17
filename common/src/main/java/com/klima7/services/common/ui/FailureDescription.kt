package com.klima7.services.common.ui

import com.klima7.services.common.R
import com.klima7.services.common.models.Failure

data class FailureDescription(val textId: Int, val imageId: Int) {

    companion object {

        private val all = mapOf(
            Failure.InternetFailure to
                    FailureDescription(R.string.failure__internet_failure_message, R.drawable.icon_error_no_internet),
            Failure.ServerFailure to
                    FailureDescription(R.string.failure__server_failure_message, R.drawable.icon_error_server),
            Failure.UnknownFailure to
                    FailureDescription(R.string.failure__unknown_failure_message, R.drawable.icon_error_unknown),
            Failure.PermissionFailure to
                    FailureDescription(R.string.failure__permission_failure_message, R.drawable.icon_error_permission),
            Failure.NotFoundFailure to
                    FailureDescription(R.string.failure__not_found_failure_message, R.drawable.icon_error_not_found),
        )

        fun get(failure: Failure): FailureDescription {
            return all[failure] ?: throw Exception("Not described failure encountered: $failure")
        }
    }

}
