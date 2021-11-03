package com.klima7.services.expert.features.offers.base

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.common.models.Offer
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentOffersListBinding
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
        Log.i("Hello", "Offer clicked $offer")
    }
}