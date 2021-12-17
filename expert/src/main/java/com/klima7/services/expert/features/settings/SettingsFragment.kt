package com.klima7.services.expert.features.settings

import android.content.Intent
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.klima7.services.common.components.settings.BaseSettingsFragment
import com.klima7.services.common.components.settings.SettingsOption
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.features.area.WorkingAreaActivity
import com.klima7.services.expert.features.credits.CreditsActivity
import com.klima7.services.expert.features.delete.DeleteActivity
import com.klima7.services.expert.features.info.InfoActivity
import com.klima7.services.expert.features.services.ServicesActivity
import com.klima7.services.expert.features.splash.SplashActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment: BaseSettingsFragment() {

    override val layoutId = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by viewModel()

    override fun getSpecificSettingsOptions(): List<SettingsOption> {
        return listOf(
            SettingsOption(R.drawable.icon_profile, R.string.settings__profile, SettingsViewModel.Event.ShowInfoScreen),
            SettingsOption(R.drawable.icon_service, R.string.settings__services, SettingsViewModel.Event.ShowServicesScreen),
            SettingsOption(R.drawable.icon_location, R.string.settings__location, SettingsViewModel.Event.ShowWorkingAreaScreen),
            SettingsOption(R.drawable.icon_delete_account, R.string.settings__delete, SettingsViewModel.Event.ShowDeleteScreen),
        )
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            SettingsViewModel.Event.ShowInfoScreen -> showInfoScreen()
            SettingsViewModel.Event.ShowWorkingAreaScreen -> showLocationScreen()
            SettingsViewModel.Event.ShowServicesScreen -> showServicesScreen()
            SettingsViewModel.Event.ShowDeleteScreen -> showDeleteScreen()
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

    private fun showDeleteScreen() {
        val intent = Intent(activity, DeleteActivity::class.java)
        startActivity(intent)
    }

    override fun showCreditsScreen() {
        val intent = Intent(activity, CreditsActivity::class.java)
        startActivity(intent)
    }

    override fun showSplashScreen() {
        val intent = Intent(activity, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
        requireActivity().finish()
        Animatoo.animateDiagonal(requireActivity())
    }
}