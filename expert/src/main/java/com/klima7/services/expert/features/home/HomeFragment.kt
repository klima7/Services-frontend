package com.klima7.services.expert.features.home

import android.content.Intent
import androidx.core.os.bundleOf
import com.klima7.services.common.components.home.BaseHomeFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.features.offer.OfferActivity
import com.klima7.services.expert.features.settings.SettingsActivity
import com.klima7.services.expert.messaging.RefreshTokenWorker
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment: BaseHomeFragment() {

    override val layoutId = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModel()

    override val navGraphId = R.navigation.nav_graph_home
    override val bottomMenuId = R.menu.home_bottom

    override fun onFirstCreation() {
        super.onFirstCreation()
        RefreshTokenWorker.schedule(requireContext())
    }

    override fun getDestinations() =
        setOf(R.id.jobsFragment, R.id.offersFragment, R.id.profileFragment)

    override fun showSettingsScreen() {
        val intent = Intent(activity, SettingsActivity::class.java)
        startActivity(intent)
    }

    override fun showOfferScreen(offerId: String) {
        val intent = Intent(requireContext(), OfferActivity::class.java)
        val bundle = bundleOf(
            "offerId" to offerId,
            "exit" to "slideRight",
        )
        intent.putExtras(bundle)
        startActivity(intent)
    }
}