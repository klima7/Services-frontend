package com.klima7.services.common.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.klima7.services.common.models.JobStatus

@Entity(primaryKeys = ["uid", "jobId"])
data class JobStatusEntity(
    val uid: String,
    val jobId: String,
    val status: Int
) {

    companion object {

        fun jobStatusToInt(jobStatus: JobStatus): Int {
            return when(jobStatus) {
                JobStatus.NEW -> 0
                JobStatus.ACCEPTED -> 1
                JobStatus.REJECTED -> 2
            }
        }

        fun intToJobStatus(int: Int): JobStatus {
            return when(int) {
                0 -> JobStatus.NEW
                1 -> JobStatus.ACCEPTED
                2 -> JobStatus.REJECTED
                else -> JobStatus.NEW
            }
        }

    }

}
