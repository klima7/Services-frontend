package com.klima7.services.client.features.newjob.location

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.LastLocationsRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.LastLocation
import kotlinx.coroutines.Dispatchers
import java.util.*

class AddLastLocationUC(
    private val lastLocationsRepository: LastLocationsRepository
): BaseUC<AddLastLocationUC.Params, None>(Dispatchers.IO) {

    data class Params(val placeId: String, val placeName: String)

    override suspend fun execute(params: Params): Outcome<Failure, None> {
        val location = LastLocation(params.placeId, params.placeName, Date())
        return lastLocationsRepository.addLocationOrUpdate(location)
    }
}