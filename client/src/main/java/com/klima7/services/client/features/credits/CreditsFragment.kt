package com.klima7.services.client.features.credits

import com.klima7.services.client.R
import com.klima7.services.common.components.credits.BaseCreditsFragment
import com.klima7.services.common.components.credits.Credit
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreditsFragment: BaseCreditsFragment() {

    override val viewModel: CreditsViewModel by viewModel()

    override fun getCredits() = listOf(
        Credit(R.drawable.pin, R.string.credit_pin),
        Credit(R.drawable.pin, R.string.credit_pin),
        Credit(R.drawable.pin, R.string.credit_pin),
    )
}