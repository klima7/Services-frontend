package com.klima7.services.client.features.settings

import android.content.Intent
import com.klima7.services.client.R
import com.klima7.services.client.features.credits.CreditsActivity
import com.klima7.services.client.features.delete.DeleteActivity
import com.klima7.services.client.features.info.InfoActivity
import com.klima7.services.client.features.splash.SplashActivity
import com.klima7.services.common.components.settings.BaseSettingsFragment
import com.klima7.services.common.components.settings.SettingsOption
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment: BaseSettingsFragment() {

    override val layoutId = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by viewModel()

    override fun getSpecificSettingsOptions(): List<SettingsOption> {
        return listOf(
            SettingsOption(R.drawable.icon_profile, R.string.settings_profile, SettingsViewModel.Event.ShowInfoScreen),
            SettingsOption(R.drawable.icon_delete_account, R.string.settings_delete, SettingsViewModel.Event.ShowDeleteScreen),
        )
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            SettingsViewModel.Event.ShowInfoScreen -> showInfoScreen()
            SettingsViewModel.Event.ShowDeleteScreen -> showDeleteScreen()
        }
    }

    private fun showInfoScreen() {
        val intent = Intent(activity, InfoActivity::class.java)
        startActivity(intent)
    }

    private fun showDeleteScreen() {
        val intent = Intent(activity, DeleteActivity::class.java)
        startActivity(intent)
    }

    override fun showSplashScreen() {
        val intent = Intent(activity, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
        requireActivity().finish()
    }

    override fun showCreditsScreen() {
        val intent = Intent(activity, CreditsActivity::class.java)
        startActivity(intent)
    }
}