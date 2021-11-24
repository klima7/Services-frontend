package com.klima7.services.client.features.newjob.location

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.data.repositories.LastLocationsRepository
import com.klima7.services.common.models.LastLocation
import kotlinx.coroutines.Dispatchers

class GetLastLocationsUC(
    private val lastLocationsRepository: LastLocationsRepository
): BaseUC<None, List<LastLocation>>(Dispatchers.IO) {

    companion object {
        const val LOCATIONS_COUNT = 10
    }

    override suspend fun execute(params: None) =
        lastLocationsRepository.getLastLocations(LOCATIONS_COUNT)

}