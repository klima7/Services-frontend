package com.klima7.services.expert.features.settings

import com.klima7.services.common.lib.base.BaseFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentSettingsBinding
import com.klima7.services.expert.features.login.LoginViewModel
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

}