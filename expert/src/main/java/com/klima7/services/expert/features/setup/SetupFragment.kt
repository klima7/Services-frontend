package com.klima7.services.expert.features.setup

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentSetupBinding
import com.klima7.services.expert.features.area.WorkingAreaActivity
import com.klima7.services.expert.features.home.HomeActivity
import com.klima7.services.expert.features.info.InfoActivity
import com.klima7.services.expert.features.services.ServicesActivity
import com.klima7.services.expert.features.splash.SplashActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class SetupFragment: BaseFragment<FragmentSetupBinding>() {

    override val layoutId = R.layout.fragment_setup
    override val viewModel: SetupViewModel by viewModel()

    private val configLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        this.onConfigDone()
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.started()
    }

    override fun init() {
        super.init()
        binding.setupToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            SetupViewModel.Event.ShowHomeScreen -> showHomeScreen()
            SetupViewModel.Event.ShowInfoScreen -> showInfoScreen()
            SetupViewModel.Event.ShowServicesScreen -> showServicesScreen()
            SetupViewModel.Event.ShowWorkingAreaScreen -> showLocationScreen()
            SetupViewModel.Event.ShowSplashScreen -> showSplashScreen()
        }
    }

    private fun showHomeScreen() {
        val intent = Intent(activity, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
        requireActivity().finish()
    }

    private fun showInfoScreen() {
        val intent = Intent(activity, InfoActivity::class.java)
        configLauncher.launch(intent)
    }

    private fun showLocationScreen() {
        val intent = Intent(activity, WorkingAreaActivity::class.java)
        configLauncher.launch(intent)
    }

    private fun showServicesScreen() {
        val intent = Intent(activity, ServicesActivity::class.java)
        configLauncher.launch(intent)
    }

    private fun showSplashScreen() {
        val intent = Intent(activity, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
        requireActivity().finish()
    }

    private fun onConfigDone() {
        viewModel.configDone()
    }

}