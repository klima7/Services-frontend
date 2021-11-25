package com.klima7.services.client.features.newjob.newjob

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentNewJobBinding
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewJobFragment: BaseFragment<FragmentNewJobBinding>() {

    override val layoutId = R.layout.fragment_new_job
    override val viewModel: NewJobViewModel by viewModel()

    private lateinit var navController: NavController

    override fun init() {
        val navHostFragment = childFragmentManager.findFragmentById(R.id.home_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

}