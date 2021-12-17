package com.klima7.services.expert.features.credits

import com.klima7.services.common.components.credits.BaseCreditsFragment
import com.klima7.services.common.components.credits.Credit
import com.klima7.services.expert.R

class CreditsFragment: BaseCreditsFragment() {

    override fun getSpecificCredits() = listOf(
        Credit(R.drawable.deal_hand, R.string.credits__deal_hand)
    )
}