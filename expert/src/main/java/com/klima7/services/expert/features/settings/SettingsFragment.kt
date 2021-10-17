package com.klima7.services.expert.features.settings

import android.content.Intent
import com.klima7.services.common.ui.base.BaseFragment
import com.klima7.services.common.ui.base.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentSettingsBinding
import com.klima7.services.expert.features.area.WorkingAreaActivity
import com.klima7.services.expert.features.info.InfoActivity
import com.klima7.services.expert.features.services.ServicesActivity
import com.klima7.services.expert.features.splash.SplashActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment: BaseFragment<FragmentSettingsBinding>() {

    override val layoutId = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by viewModel()

    override fun init() {
        val toolbar = binding.settingsToolbar
        toolbar.title = "Ustawienia"
        toolbar.setNavigationIcon(R.drawable.icon_arrow_back)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            SettingsViewModel.Event.ShowInfoScreen -> showInfoScreen()
            SettingsViewModel.Event.ShowWorkingAreaScreen -> showLocationScreen()
            SettingsViewModel.Event.ShowServicesScreen -> showServicesScreen()
            SettingsViewModel.Event.ShowSplashScreen -> showSplashScreen()
        }
    }

    private fun showInfoScreen() {
        val intent = Intent(activity, InfoActivity::class.java)
        startActivity(intent)
    }

    private fun showLocationScreen() {
        val intent = Intent(activity, WorkingAreaActivity::class.java)
        startActivity(intent)
    }

    private fun showServicesScreen() {
        val intent = Intent(activity, ServicesActivity::class.java)
        startActivity(intent)
    }

    private fun showSplashScreen() {
        val intent = Intent(activity, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
        requireActivity().finish()
    }
}