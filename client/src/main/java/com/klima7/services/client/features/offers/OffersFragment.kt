package com.klima7.services.client.features.offers

import android.util.Log
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentOffersBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OffersFragment: BaseFragment<FragmentOffersBinding>() {

    override val layoutId = R.layout.fragment_offers
    override val viewModel: OffersViewModel by viewModel()

    lateinit var jobId: String

    override fun onFirstCreation() {
        super.onFirstCreation()
        jobId = arguments?.getString("jobId") ?: throw Error("JobId argument not supplied")
        viewModel.start(jobId)
    }

    override fun init() {
        super.init()
        binding.offersToolbar.apply {
            setNavigationOnClickListener { requireActivity().onBackPressed() }
            setOnMenuItemClickListener {
                viewModel.showDetailsClicked()
                true
            }
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is OffersViewModel.Event.ShowJobDetails -> showJobDetails(event.jobId)
        }
    }

    private fun showJobDetails(jobId: String) {
        Log.i("Hello", "Showing job details: $jobId")
    }
}