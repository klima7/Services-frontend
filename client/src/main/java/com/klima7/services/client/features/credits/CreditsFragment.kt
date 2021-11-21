package com.klima7.services.client.features.credits

import com.klima7.services.common.components.credits.BaseCreditsFragment
import com.klima7.services.common.components.credits.Credit

class CreditsFragment: BaseCreditsFragment() {

    override fun getSpecificCredits() = listOf<Credit>()
}