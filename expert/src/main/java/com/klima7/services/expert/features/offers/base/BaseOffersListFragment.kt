package com.klima7.services.expert.features.offers.base

import android.content.Intent
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.klima7.services.common.models.Offer
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentOffersListBinding
import com.klima7.services.expert.features.job.JobActivity
import com.klima7.services.expert.features.offer.OfferActivity
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseOffersListFragment(
    private val swipeColorResource: Int,
    private val swipeIconResource: Int,
): BaseFragment<FragmentOffersListBinding>(),
    OffersAdapter.OnOfferListener {

    override val layoutId = R.layout.fragment_offers_list
    abstract override val viewModel: BaseOffersListViewModel

    private lateinit var offersAdapter: OffersAdapter

    override fun init() {
        super.init()

        offersAdapter = OffersAdapter(this)
        binding.offersLoadList.apply {
            this.adapter = offersAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest { pagingData ->
                offersAdapter.submitData(pagingData)
            }
        }

        (ItemTouchHelper(itemTouchHelperCallback)).attachToRecyclerView(binding.offersLoadList.recycler)
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

    private val itemTouchHelperCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val offer = offersAdapter.getOffer(position)
                if (offer != null) {
                    viewModel.offerSwiped(offer.id)
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c, recyclerView, viewHolder, dX, dY,
                    actionState, isCurrentlyActive
                )
                    .addBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            swipeColorResource
                        )
                    )
                    .addActionIcon(swipeIconResource)
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
}