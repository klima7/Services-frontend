package com.klima7.services.client.features.jobsetup

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentNewJobBinding
import com.klima7.services.client.features.home.HomeActivity
import com.klima7.services.common.models.Category
import com.klima7.services.common.models.SimpleService
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class JobSetupFragment: BaseFragment<FragmentNewJobBinding>() {

    override val layoutId = R.layout.fragment_new_job
    override val viewModel: JobSetupViewModel by viewModel()

    private lateinit var navController: NavController

    override fun onFirstCreation() {
        super.onFirstCreation()
        val categoryId = arguments?.getString("categoryId")
        val categoryName = arguments?.getString("categoryName")
        val serviceId = arguments?.getString("serviceId")
        val serviceName = arguments?.getString("serviceName")

        when {
            categoryId != null && categoryName != null -> {
                viewModel.startService(Category(categoryId, categoryName))
            }
            serviceId != null && serviceName != null -> {
                viewModel.startLocation(SimpleService(serviceId, serviceName))
            }
            else -> throw Error("Invalid arguments provided to NewJobActivity")
        }
    }

    override fun init() {
        binding.newjobToolbar.setNavigationOnClickListener { backButtonClicked() }

        val navHostFragment = childFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    fun backButtonClicked() {
        viewModel.backClicked()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is JobSetupViewModel.Event.Navigate -> navigate(event.screen, event.direction)
            is JobSetupViewModel.Event.ShowJobsScreen -> showJobsScreen()
        }
    }

    private fun navigate(screen: JobSetupViewModel.Screen, direction: JobSetupViewModel.Direction) {
        val destination = when(screen) {
            JobSetupViewModel.Screen.SERVICE -> R.id.serviceFragment
            JobSetupViewModel.Screen.LOCATION -> R.id.locationFragment
            JobSetupViewModel.Screen.JOB_DETAILS -> R.id.jobDetailsFragment
            JobSetupViewModel.Screen.JOB_CREATED -> R.id.jobCreatedFragment2
        }

        val animation = when(direction) {
            JobSetupViewModel.Direction.FORTH -> Pair(
                com.blogspot.atifsoftwares.animatoolib.R.anim.animate_slide_left_enter,
                com.blogspot.atifsoftwares.animatoolib.R.anim.animate_slide_left_exit)
            JobSetupViewModel.Direction.BACK -> Pair(
                com.blogspot.atifsoftwares.animatoolib.R.anim.animate_slide_in_left,
                com.blogspot.atifsoftwares.animatoolib.R.anim.animate_slide_out_right
            )
        }

        val options = NavOptions.Builder()
            .setEnterAnim(animation.first)
            .setExitAnim(animation.second)
            .build()

        navController.navigate(destination, null, options)
    }

    private fun showJobsScreen() {
        val intent = Intent(activity, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}