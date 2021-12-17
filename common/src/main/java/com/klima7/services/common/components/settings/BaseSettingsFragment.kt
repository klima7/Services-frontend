package com.klima7.services.common.components.settings

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.common.R
import com.klima7.services.common.components.yesnodialog.YesNoDialogFragment
import com.klima7.services.common.databinding.FragmentSettingsBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.groupiex.plusAssign

abstract class BaseSettingsFragment:
    BaseFragment<FragmentSettingsBinding>(),
    SettingsOptionItem.OnClickListener {

    companion object {
        const val LOGOUT_QUERY_DIALOG_KEY = "LOGOUT_QUERY_DIALOG_KEY"
    }

    override val layoutId = R.layout.fragment_settings
    abstract override val viewModel: BaseSettingsViewModel

    private val adapter = GroupieAdapter()

    override fun init() {
        childFragmentManager.setFragmentResultListener(LOGOUT_QUERY_DIALOG_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(YesNoDialogFragment.BUNDLE_KEY)
            if(result == YesNoDialogFragment.Result.YES) {
                viewModel.logoutConfirmed()
            }
        }

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
            SettingsOption(R.drawable.icon_heart, R.string.settings__credits, BaseSettingsViewModel.Event.ShowCreditsScreen),
            SettingsOption(R.drawable.icon_logout, R.string.settings__logout, BaseSettingsViewModel.Event.ShowSplashScreen),
        )
    }

    abstract fun getSpecificSettingsOptions(): List<SettingsOption>

    override fun settingsOptionClicked(settingsOption: SettingsOption) {
        viewModel.settingsOptionClicked(settingsOption)
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            BaseSettingsViewModel.Event.ShowLogoutQuery -> showLogoutQuery()
            BaseSettingsViewModel.Event.ShowSplashScreen -> showSplashScreen()
            BaseSettingsViewModel.Event.ShowCreditsScreen -> showCreditsScreen()
        }
    }

    private fun showLogoutQuery() {
        val dialog = YesNoDialogFragment.create(
            LOGOUT_QUERY_DIALOG_KEY,
            requireContext().getString(R.string.settings__logout_ensure_message)
        )
        dialog.show(childFragmentManager, "YesNoDialogFragment")
    }

    abstract fun showSplashScreen()

    abstract fun showCreditsScreen()
}