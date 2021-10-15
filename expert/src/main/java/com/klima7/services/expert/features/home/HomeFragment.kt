package com.klima7.services.expert.features.home

import android.content.Intent
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentHomeBinding
import com.klima7.services.expert.features.settings.SettingsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    override val layoutId = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()

    override fun init() {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.homeBottomNav.setupWithNavController(navController)
        val destinations = setOf(R.id.jobsFragment, R.id.offersFragment, R.id.profileFragment)
        val appBarConfiguration = AppBarConfiguration.Builder(destinations).build()
        binding.homeToolbar.setupWithNavController(navController, appBarConfiguration)

        binding.homeToolbar.inflateMenu(R.menu.menu_home_toolbar)
        binding.homeToolbar.setOnMenuItemClickListener {
            viewModel.settingsIconClicked()
            true
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            HomeViewModel.Event.ShowSettingsScreen -> showSettingsScreen()
        }
    }

    private fun showSettingsScreen() {
        val intent = Intent(activity, SettingsActivity::class.java)
        startActivity(intent)
    }
}