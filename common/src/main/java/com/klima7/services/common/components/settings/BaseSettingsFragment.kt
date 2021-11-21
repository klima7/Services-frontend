package com.klima7.services.common.components.settings

import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentSettingsBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.groupiex.plusAssign

abstract class BaseSettingsFragment:
    BaseFragment<FragmentSettingsBinding>(),
    SettingsOptionItem.OnClickListener {

    override val layoutId = R.layout.fragment_settings
    abstract override val viewModel: BaseSettingsViewModel

    private val adapter = GroupieAdapter()

    override fun init() {
        binding.settingsToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        val recycler = binding.settingsRecycler
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        for(settingsOption in getSettingsOptions()) {
            adapter += SettingsOptionItem(settingsOption, this)
        }
    }

    private fun getSettingsOptions(): List<SettingsOption> {
        return getSpecificSettingsOptions() + getCommonSettingsOptions()
    }

    private fun getCommonSettingsOptions(): List<SettingsOption> {
        return listOf(
            SettingsOption(R.drawable.icon_heart, R.string.settings_credits, BaseSettingsViewModel.Event.ShowCreditsScreen),
            SettingsOption(R.drawable.icon_logout, R.string.settings_logout, BaseSettingsViewModel.Event.ShowSplashScreen),
        )
    }

    abstract fun getSpecificSettingsOptions(): List<SettingsOption>

    override fun settingsOptionClicked(settingsOption: SettingsOption) {
        viewModel.settingsOptionClicked(settingsOption)
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            BaseSettingsViewModel.Event.ShowSplashScreen -> showSplashScreen()
            BaseSettingsViewModel.Event.ShowCreditsScreen -> showCreditsScreen()
        }
    }

    abstract fun showSplashScreen()

    abstract fun showCreditsScreen()
}