package com.klima7.services.client.features.newjob.jobdetails

import androidx.lifecycle.MutableLiveData
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentJobDetailsBinding
import com.klima7.services.client.databinding.FragmentServiceBinding
import com.klima7.services.client.features.newjob.newjob.NewJobViewModel
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class JobDetailsFragment: BaseFragment<FragmentJobDetailsBinding>() {

    override val layoutId = R.layout.fragment_job_details
    override val viewModel: JobDetailsViewModel by viewModel()

    private val parentViewModel: NewJobViewModel by lazy {
        requireParentFragment().requireParentFragment().getViewModel()
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
//        val serviceId = arguments?.getString("serviceId") ?: throw Error("serviceId argument not supplied")
//        val serviceName = arguments?.getString("serviceName") ?: throw Error("serviceName argument not supplied")
//        val locationId = arguments?.getString("locationId") ?: throw Error("locationId argument not supplied")
//        val locationName = arguments?.getString("locationName") ?: throw Error("locationName argument not supplied")
        viewModel.start()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            JobDetailsViewModel.Event.ShowJobCreatedScreen -> showJobCreatedScreen()
        }
    }

    fun showJobCreatedScreen() {
        parentViewModel.showCreatedScreen()
    }

}