package com.klima7.services.client.features.profile

import com.klima7.services.common.components.profile.BaseProfileFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileContentFragment: BaseProfileFragment() {

    override val viewModel: ProfileContentViewModel by viewModel()

}