package com.klima7.services.expert.features.profile

import android.os.Bundle
import android.view.View
import com.klima7.services.common.components.profile.BaseProfileFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment: BaseProfileFragment() {

    override val viewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.start()
    }
}