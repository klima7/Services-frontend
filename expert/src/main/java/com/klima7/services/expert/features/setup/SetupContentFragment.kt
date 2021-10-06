package com.klima7.services.expert.features.setup

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.common.lib.failurable.FailurableFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentSetupBinding
import com.klima7.services.expert.features.home.HomeActivity
import com.klima7.services.expert.features.info.InfoActivity
import com.klima7.services.expert.features.location.LocationActivity
import com.klima7.services.expert.features.services.ServicesActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class SetupContentFragment: FailurableFragment<FragmentSetupBinding>() {

    override val layoutId = R.layout.fragment_setup
    override val viewModel: SetupContentViewModel by viewModel()

    private val configLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        this.onConfigDone()
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.setupStarted()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            SetupContentViewModel.Event.ShowHomeScreen -> showHomeScreen()
            SetupContentViewModel.Event.ShowInfoScreen -> showInfoScreen()
            SetupContentViewModel.Event.ShowServicesScreen -> showServicesScreen()
            SetupContentViewModel.Event.ShowLocationScreen -> showLocationScreen()
        }
    }

    private fun showHomeScreen() {
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun showInfoScreen() {
        val intent = Intent(activity, InfoActivity::class.java)
        configLauncher.launch(intent)
    }

    private fun showLocationScreen() {
        val intent = Intent(activity, LocationActivity::class.java)
        configLauncher.launch(intent)
    }

    private fun showServicesScreen() {
        val intent = Intent(activity, ServicesActivity::class.java)
        configLauncher.launch(intent)
    }

    private fun onConfigDone() {
        viewModel.configDone()
    }

}