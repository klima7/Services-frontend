package com.klima7.services.expert.features.offers.base

import android.content.Intent
import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.common.models.Offer
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentOffersListBinding
import com.klima7.services.expert.features.job.JobActivity
import com.klima7.services.expert.features.offer.OfferActivity
import kotlinx.coroutines.launch

abstract class BaseOffersListFragment: BaseFragment<FragmentOffersListBinding>(),
    OffersAdapter.OnOfferListener {

    override val layoutId = R.layout.fragment_offers_list
    abstract override val viewModel: BaseOffersListViewModel

    override fun init() {
        super.init()

        val adapter = OffersAdapter(this)
        binding.offersLoadList.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.pagingData.observe(viewLifecycleOwner) { pagingData ->
            lifecycleScope.launch {
                adapter.submitData(lifecycle, pagingData)
            }
        }
    }

    override fun onOfferClicked(offer: Offer) {
        val intent = Intent(requireContext(), OfferActivity::class.java)
        val bundle = bundleOf("offerId" to offer.id)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onShowJobClicked(offer: Offer) {
        val intent = Intent(requireContext(), JobActivity::class.java)
        val bundle = bundleOf("jobId" to offer.jobId)
        intent.putExtras(bundle)
        startActivity(intent)
    }
}