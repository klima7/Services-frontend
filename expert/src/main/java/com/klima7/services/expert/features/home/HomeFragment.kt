package com.klima7.services.expert.features.home

import android.content.Intent
import com.klima7.services.common.components.home.BaseHomeFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.features.settings.SettingsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment: BaseHomeFragment() {

    override val layoutId = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()

    override fun showSettingsScreen() {
        val intent = Intent(activity, SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun getDestinations() =
        setOf(R.id.jobsFragment, R.id.offersFragment, R.id.profileFragment)
}