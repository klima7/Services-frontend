package com.klima7.services.common.data.repositories

import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.firebase.FirebaseSource
import com.klima7.services.common.models.Expert
import com.klima7.services.common.models.ExpertInfo
import com.klima7.services.common.models.Failure

class ExpertsRepository(
    private val firebase: FirebaseSource,
) {

    suspend fun createExpertAccount(): Outcome<Failure, None> {
        return firebase.expertsDao.createExpertAccount()
    }

    suspend fun getExpert(uid: String): Outcome<Failure, Expert> {
        return firebase.expertsDao.getExpert(uid)
    }

    suspend fun setExpertInfo(info: ExpertInfo): Outcome<Failure, None> {
        return firebase.expertsDao.setExpertInfo(info)
    }

    suspend fun setProfileImage(profileImageUrl: String): Outcome<Failure, None> {
        return firebase.expertsDao.setProfileImage(profileImageUrl)
    }

    suspend fun clearProfileImage(): Outcome<Failure, None> {
        return firebase.expertsDao.clearProfileImage()
    }

    suspend fun setServicesIds(services: List<String>): Outcome<Failure, None> {
        return firebase.expertsDao.setServicesIds(services)
    }

    suspend fun setWorkingArea(placeId: String, radius: Int): Outcome<Failure, None> {
        return firebase.expertsDao.setWorkingArea(placeId, radius)
    }

    suspend fun deleteAccount(): Outcome<Failure, None> {
        return firebase.expertsDao.deleteAccount()
    }
}