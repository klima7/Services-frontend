package com.klima7.services.client.features.home

import android.content.Intent
import android.util.Log
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentHomeBinding
import com.klima7.services.client.features.delete.DeleteActivity
import com.klima7.services.client.features.offer.OfferActivity
import com.klima7.services.client.features.settings.SettingsActivity
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    override val layoutId = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()

    override fun init() {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.homeBottomNav.setupWithNavController(navController)
        val destinations = setOf(R.id.jobsFragment, R.id.searchFragment)
        val appBarConfiguration = AppBarConfiguration.Builder(destinations).build()
        binding.homeToolbar.setupWithNavController(navController, appBarConfiguration)

        // Do nothing on selecting same item
        binding.homeBottomNav.setOnItemReselectedListener {}

        binding.homeToolbar.inflateMenu(R.menu.menu_home_toolbar)
        binding.homeToolbar.setOnMenuItemClickListener {
            _temp_start_offer("offer1")
//            viewModel.settingsIconClicked()
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

    private fun _temp_start_offer(offerId: String) {
        val intent = Intent(requireContext(), OfferActivity::class.java)
        val bundle = bundleOf("offerId" to offerId)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}