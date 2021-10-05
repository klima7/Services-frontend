package com.klima7.services.expert.features.setup

import android.content.Intent
import android.widget.Button
import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentSetupBinding
import com.klima7.services.expert.features.home.HomeActivity
import com.klima7.services.expert.features.info.InfoActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class SetupFragment: BaseFragment<FragmentSetupBinding>() {

    override val layoutId = R.layout.fragment_setup
    override val viewModel: SetupViewModel by viewModel()

    override fun init() {
        binding.setupToolbar.title = "Uzupełnienie informacji"
        view?.findViewById<Button>(R.id.no_internet_refresh_button)?.setOnClickListener { viewModel.refreshClicked() }
        viewModel.setupStarted()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        when(event) {
            SetupViewModel.Event.ShowHomeScreen -> showHomeScreen()
            SetupViewModel.Event.ShowInfoScreen -> showInfoScreen()
            SetupViewModel.Event.ShowServicesScreen -> showServicesScreen()
            SetupViewModel.Event.ShowLocationScreen -> showLocationScreen()
        }
    }

    private fun showHomeScreen() {
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun showInfoScreen() {
        val intent = Intent(activity, InfoActivity::class.java)
        startActivity(intent)
    }

    private fun showLocationScreen() {

    }

    private fun showServicesScreen() {

    }

}