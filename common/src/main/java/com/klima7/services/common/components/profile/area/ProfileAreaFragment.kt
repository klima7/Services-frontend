package com.klima7.services.common.components.profile.area

import android.content.Intent
import android.net.Uri
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileAreaBinding
import com.klima7.services.common.models.Coordinates
import com.klima7.services.common.components.areavisual.AreaVisualizationFragment
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.common.components.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileAreaFragment: BaseFragment<FragmentProfileAreaBinding>() {

    override val layoutId = R.layout.fragment_profile_area
    override val viewModel: ProfileAreaViewModel by viewModel()
    private val profileViewModel  by lazy {
        requireParentFragment().getViewModel<ProfileViewModel>()
    }

    override fun init() {
        super.init()

        val areaVisualization = childFragmentManager.
        findFragmentById(R.id.profile_area_visualization_fragment) as AreaVisualizationFragment

        profileViewModel.expert.observe(viewLifecycleOwner) { expert ->
            areaVisualization.setArea(expert.area)
            viewModel.setArea(expert.area)
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is ProfileAreaViewModel.Event.OpenLocationInGoogleMaps -> openInGoogleMaps(event.coords)
        }
    }

    private fun openInGoogleMaps(coords: Coordinates) {
        val gmmIntentUri = Uri.parse("geo:${coords.latitude},${coords.longitude}?q=${coords.latitude},${coords.longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}