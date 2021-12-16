package com.klima7.services.client.features.home

import android.content.Intent
import com.klima7.services.client.R
import com.klima7.services.client.features.settings.SettingsActivity
import com.klima7.services.common.components.home.BaseHomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment: BaseHomeFragment() {

    override val layoutId = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()

    override fun getDestinations() = setOf(R.id.jobsFragment, R.id.searchFragment)

    override fun showSettingsScreen() {
        val intent = Intent(activity, SettingsActivity::class.java)
        startActivity(intent)
    }

}