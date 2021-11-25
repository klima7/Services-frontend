package com.klima7.services.client.features.jobsetup.location

import com.klima7.services.common.core.BaseUC
import com.klima7.services.common.core.None
import com.klima7.services.common.core.Outcome
import com.klima7.services.common.data.repositories.LastLocationsRepository
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.LastLocation
import com.klima7.services.common.models.SimpleLocation
import kotlinx.coroutines.Dispatchers
import java.util.*

class AddLastLocationUC(
    private val lastLocationsRepository: LastLocationsRepository
): BaseUC<AddLastLocationUC.Params, None>(Dispatchers.IO) {

    data class Params(val location: SimpleLocation)

    override suspend fun execute(params: Params): Outcome<Failure, None> {
        val location = LastLocation(params.location, Date())
        return lastLocationsRepository.addLocationOrUpdate(location)
    }
}
