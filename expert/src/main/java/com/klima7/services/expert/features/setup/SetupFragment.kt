package com.klima7.services.expert.features.setup

import android.content.Intent
import android.widget.Button
import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentSetupBinding
import com.klima7.services.expert.features.home.HomeActivity
import com.klima7.services.expert.features.splash.SplashViewModel
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
            SplashViewModel.Event.ShowHomeScreen -> showHomeScreen()
        }
    }

    private fun showHomeScreen() {
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
    }

}