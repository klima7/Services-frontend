package com.klima7.services.client.features.newjob.newjob

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentNewJobBinding
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.klima7.services.common.models.Category
import com.klima7.services.common.models.SimpleService
import com.klima7.services.common.platform.BaseViewModel


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

    fun showServiceScreen(category: Category) {

    }

    fun showLocationScreen(service: SimpleService) {

    }

    fun showDescriptionScreen(service: SimpleService) {

    }

    private fun updateScreen(newScreen: NewJobViewModel.Screen) {
        val currentId = navController.currentDestination?.id
        Log.i("Hello", "Updating screen to $newScreen")
        val nextId = when(newScreen) {
            NewJobViewModel.Screen.SERVICE -> R.id.serviceFragment
            NewJobViewModel.Screen.LOCATION -> R.id.locationFragment
            NewJobViewModel.Screen.DETAILS -> R.id.jobDetailsFragment
        }
        navController.navigate(nextId)
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is NewJobViewModel.Event.Navigate -> navigate(event.screen, event.direction)
        }
    }

    private fun navigate(screen: NewJobViewModel.Screen, direction: NewJobViewModel.Direction) {
        val destination = when(screen) {
            NewJobViewModel.Screen.SERVICE -> R.id.serviceFragment
            NewJobViewModel.Screen.LOCATION -> R.id.locationFragment
            NewJobViewModel.Screen.DETAILS -> R.id.jobDetailsFragment
        }

        val animation = when(direction) {
            NewJobViewModel.Direction.FORTH -> Pair(
                com.blogspot.atifsoftwares.animatoolib.R.anim.animate_slide_left_enter,
                com.blogspot.atifsoftwares.animatoolib.R.anim.animate_slide_left_exit)
            NewJobViewModel.Direction.BACK -> Pair(
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
}