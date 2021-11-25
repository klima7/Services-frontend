package com.klima7.services.client.features.newjob.newjob

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentNewJobBinding
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.activity.OnBackPressedCallback





class NewJobFragment: BaseFragment<FragmentNewJobBinding>() {

    override val layoutId = R.layout.fragment_new_job
    override val viewModel: NewJobViewModel by viewModel()

    private lateinit var navController: NavController

    override fun onFirstCreation() {
        super.onFirstCreation()
        val categoryId = arguments?.getString("categoryId")
        val categoryName = arguments?.getString("categoryName")
        val serviceId = arguments?.getString("serviceId")
        val serviceName = arguments?.getString("serviceName")
    }

    override fun init() {
        binding.newjobToolbar.setNavigationOnClickListener { backButtonClicked() }

        val navHostFragment = childFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        viewModel.screen.observe(viewLifecycleOwner, this::updateScreen)
    }

    fun backButtonClicked() {
        viewModel.backClicked()
    }

    private fun updateScreen(newScreen: NewJobViewModel.Screen) {
        Log.i("Hello", "Updating screen to $newScreen")
        val id = when(newScreen) {
            NewJobViewModel.Screen.SERVICE -> R.id.serviceFragment
            NewJobViewModel.Screen.LOCATION -> R.id.locationFragment
            NewJobViewModel.Screen.DETAILS -> R.id.jobDetailsFragment
        }
        navController.navigate(id)
    }

}