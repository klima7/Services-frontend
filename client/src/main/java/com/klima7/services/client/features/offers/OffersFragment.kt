package com.klima7.services.client.features.offers

import android.content.Intent
import android.util.Log
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentOffersBinding
import com.klima7.services.client.features.job.JobActivity
import com.klima7.services.client.features.offer.OfferActivity
import com.klima7.services.client.features.profile.ProfileActivity
import com.klima7.services.common.models.OfferWithExpert
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import org.koin.androidx.viewmodel.ext.android.viewModel

class OffersFragment: BaseFragment<FragmentOffersBinding>(), OfferWithExpertItem.Listener {

    override val layoutId = R.layout.fragment_offers
    override val viewModel: OffersViewModel by viewModel()

    lateinit var jobId: String

    private val groupieAdapter = GroupieAdapter()
    private val activeSection = Section()
    private val offersSection = Section()

    override fun onFirstCreation() {
        super.onFirstCreation()
        jobId = arguments?.getString("jobId") ?: throw Error("JobId argument not supplied")
        viewModel.start(jobId)
    }

    override fun init() {
        super.init()

        groupieAdapter.add(activeSection)
        groupieAdapter.add(offersSection)

        binding.offersToolbar.apply {
            setNavigationOnClickListener { requireActivity().onBackPressed() }
            setOnMenuItemClickListener {
                viewModel.showDetailsClicked()
                true
            }
        }

        binding.offersRecycler.apply {
            adapter = groupieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.offersWithExperts.observe(viewLifecycleOwner, this::updateOffers)

        viewModel.isJobActive.observe(viewLifecycleOwner, this::updateActive)

        binding.offersRefreshLayout.setOnRefreshListener {
            binding.offersRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }
    }

    private fun updateOffers(offers: List<OfferWithExpert>) {
        offersSection.clear()
        for(offer in offers) {
            val item = OfferWithExpertItem(offer, this)
            offersSection.add(item)
        }
    }

    private fun updateActive(offerActive: Boolean) {
        activeSection.clear()
        if(offerActive) {
            activeSection.add(JobActiveItem())
        }
        else {
            activeSection.add(JobFinishedItem())
        }
    }

    override fun offerContentClicked(offerWithExpert: OfferWithExpert) {
        val intent = Intent(requireContext(), OfferActivity::class.java)
        val bundle = bundleOf("offerId" to offerWithExpert.offer.id)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun offerExpertClicked(offerWithExpert: OfferWithExpert) {
        val intent = Intent(requireContext(), ProfileActivity::class.java)
        val bundle = bundleOf("expertUid" to offerWithExpert.expert.uid)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is OffersViewModel.Event.ShowJobDetails -> showJobDetails(event.jobId)
        }
    }

    private fun showJobDetails(jobId: String) {
        val intent = Intent(requireContext(), JobActivity::class.java)
        val bundle = bundleOf("jobId" to jobId)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}