package com.klima7.services.expert.features.offers

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.tabs.TabLayout
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentOffersBinding
import com.klima7.services.expert.features.offers.base.BaseOffersListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class OffersFragment : BaseFragment<FragmentOffersBinding>(), TabLayout.OnTabSelectedListener {

    override val layoutId = R.layout.fragment_offers
    override val viewModel: OffersViewModel by viewModel()
    private lateinit var navController: NavController

    override fun init() {
        super.init()

        val navHostFragment = childFragmentManager.findFragmentById(R.id.offers_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        selectProperTab()

        binding.offersTabs.addOnTabSelectedListener(this)

        binding.offersRefreshLayout.setOnRefreshListener {
            binding.offersRefreshLayout.isRefreshing = false
            val fragment = navHostFragment.childFragmentManager.fragments[0] as BaseOffersListFragment
            fragment.refresh()
        }

        viewModel.refreshEnabled.observe(viewLifecycleOwner) { refreshEnabled ->
            binding.offersRefreshLayout.isEnabled = refreshEnabled
        }
    }

    private fun selectProperTab() {
        val tabIndex = when (viewModel.selectedTab.value) {
            OffersViewModel.Tab.Current -> 0
            OffersViewModel.Tab.Archive -> 1
            else -> 0
        }
        binding.offersTabs.getTabAt(tabIndex)?.select()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            OffersViewModel.Event.ShowCurrentTab -> showCurrentTab()
            OffersViewModel.Event.ShowArchiveTab -> showArchiveTab()
        }
    }

    private fun showCurrentTab() {
        navController.navigate(R.id.currentOffersListFragment)
    }

    private fun showArchiveTab() {
        navController.navigate(R.id.archiveOffersListFragment)
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        if(tab == null)
            return

        if(tab.position == 0) {
            viewModel.currentTabSelected()
        }
        else if(tab.position == 1) {
            viewModel.archiveTabSelected()
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}
}
