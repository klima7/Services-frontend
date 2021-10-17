package com.klima7.services.expert.features.setup

import com.klima7.services.common.domain.models.Expert
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.domain.utils.BaseUC
import com.klima7.services.common.domain.utils.None
import com.klima7.services.common.domain.utils.Outcome
import com.klima7.services.expert.common.domain.usecases.GetCurrentExpertUC

class GetCurrentExpertSetupStateUC(
    private val getCurrentExpertUC: GetCurrentExpertUC
): BaseUC<None, ExpertSetupState>() {

    override suspend fun execute(params: None): Outcome<Failure, ExpertSetupState> {
        return getExpertPart();
    }

    private suspend fun getExpertPart(): Outcome<Failure, ExpertSetupState> {
        return getCurrentExpertUC.execute(None()).foldS({ failure ->
            Outcome.Failure(failure)
        }, { expert ->
            if(expert.fromCache) {
                Outcome.Failure(Failure.InternetFailure)
            }
            else {
                analyseExpertPart(expert)
            }
        })
    }

    private fun analyseExpertPart(expert: Expert): Outcome<Failure, ExpertSetupState> {
        val infoConfigured = expert.info.name != null
        val servicesConfigured = expert.servicesIds.isNotEmpty()
        val locationConfigured = expert.area != null
        return Outcome.Success(ExpertSetupState(infoConfigured, servicesConfigured, locationConfigured))
    }

}