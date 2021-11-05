package com.klima7.services.client.features.settings

import android.content.Intent
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentSettingsBinding
import com.klima7.services.client.features.delete.DeleteActivity
import com.klima7.services.client.features.info.InfoActivity
import com.klima7.services.client.features.splash.SplashActivity
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
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
            SettingsViewModel.Event.ShowDeleteScreen -> showDeleteScreen()
            SettingsViewModel.Event.ShowSplashScreen -> showSplashScreen()
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

    private fun showSplashScreen() {
        val intent = Intent(activity, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
        requireActivity().finish()
    }
}