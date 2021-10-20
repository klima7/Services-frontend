package com.klima7.services.common.ui.profile.area

import android.util.Log
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileAreaBinding
import com.klima7.services.common.ui.areavis.AreaVisualizationFragment
import com.klima7.services.common.ui.base.BaseFragment
import com.klima7.services.common.ui.base.BaseViewModel
import com.klima7.services.common.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ProfileAreaFragment: BaseFragment<FragmentProfileAreaBinding>() {

    override val layoutId = R.layout.fragment_profile_area
    override val viewModel = BaseViewModel()
    private val profileViewModel  by lazy {
        requireParentFragment().getViewModel<ProfileViewModel>()
    }

    override fun init() {
        super.init()

        val areaVisualization = childFragmentManager.
        findFragmentById(R.id.profile_area_visualization_fragment) as AreaVisualizationFragment

        profileViewModel.expert.observe(viewLifecycleOwner) { expert ->
            Log.i("Hello", "Expert changed to $expert")
            areaVisualization.setArea(expert.area)
        }
    }
}