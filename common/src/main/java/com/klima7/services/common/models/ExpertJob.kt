package com.klima7.services.common.models

data class ExpertJob(
    val job: Job,
    val status: JobStatus,
    val isPreferred: Boolean
)
