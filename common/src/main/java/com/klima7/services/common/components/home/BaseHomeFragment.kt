package com.klima7.services.common.components.home

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentHomeBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel


abstract class BaseHomeFragment: BaseFragment<FragmentHomeBinding>() {

    override val layoutId = R.layout.fragment_home
    abstract override val viewModel: BaseHomeViewModel

    override fun onFirstCreation() {
        super.onFirstCreation()
        val offerId = arguments?.getString("offerId")
        viewModel.start(offerId)
    }

    override fun init() {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.homeBottomNav.setupWithNavController(navController)
        val destinations = getDestinations()
        val appBarConfiguration = AppBarConfiguration.Builder(destinations).build()
        binding.homeToolbar.setupWithNavController(navController, appBarConfiguration)

        // Do nothing on selecting same item
        binding.homeBottomNav.setOnItemReselectedListener {}

        binding.homeToolbar.inflateMenu(R.menu.menu_home_toolbar)
        binding.homeToolbar.setOnMenuItemClickListener {
            viewModel.settingsIconClicked()
            true
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            BaseHomeViewModel.Event.ShowSettingsScreen -> showSettingsScreen()
            is BaseHomeViewModel.Event.ShowOfferScreen -> showOfferScreen(event.offerId)
        }
    }

    abstract fun showSettingsScreen()

    abstract fun showOfferScreen(offerId: String)

    abstract fun getDestinations(): Set<Int>

}