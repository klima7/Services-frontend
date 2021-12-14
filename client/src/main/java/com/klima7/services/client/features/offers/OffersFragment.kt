package com.klima7.services.client.features.offers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentOffersBinding
import com.klima7.services.client.features.job.JobActivity
import com.klima7.services.client.features.offer.OfferActivity
import com.klima7.services.client.features.profile.ProfileActivity
import com.klima7.services.common.components.faildialog.FailureDialogFragment
import com.klima7.services.common.components.views.AvatarView
import com.klima7.services.common.components.views.StatusSelectionView
import com.klima7.services.common.components.yesnodialog.YesNoDialogFragment
import com.klima7.services.common.models.Failure
import com.klima7.services.common.models.OfferStatus
import com.klima7.services.common.models.OfferWithExpert
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import org.koin.androidx.viewmodel.ext.android.viewModel

class OffersFragment: BaseFragment<FragmentOffersBinding>(), OfferWithExpertItem.Listener,
    JobActiveItem.Listener, StatusSelectionView.Listener {

    companion object {
        const val RESULT_FINISHED = "FINISHED"
        const val FINISH_JOB_ENSURE_DIALOG_KEY = "FINISH_JOB_ENSURE_DIALOG_KEY"
        const val FINISH_JOB_FAILURE_DIALOG_KEY = "FINISH_JOB_FAILURE_DIALOG_KEY"
    }

    override val layoutId = R.layout.fragment_offers
    override val viewModel: OffersViewModel by viewModel()

    lateinit var jobId: String

    private val groupieAdapter = GroupieAdapter()
    private val activeSection = Section()
    private val filterSection = Section()
    private val offersSection = Section()

    private val statusFilterItem = OfferStatusFilterItem(this)

    override fun onFirstCreation() {
        super.onFirstCreation()
        jobId = arguments?.getString("jobId") ?: throw Error("JobId argument not supplied")
        viewModel.start(jobId)
    }

    override fun init() {
        super.init()

        childFragmentManager.setFragmentResultListener(FINISH_JOB_FAILURE_DIALOG_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(YesNoDialogFragment.BUNDLE_KEY)
            if(result == FailureDialogFragment.Result.RETRY) {
                viewModel.retryFinishJob()
            }
        }

        childFragmentManager.setFragmentResultListener(FINISH_JOB_ENSURE_DIALOG_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(YesNoDialogFragment.BUNDLE_KEY)
            if(result == YesNoDialogFragment.Result.YES) {
                viewModel.finishJobConfirmed()
            }
        }

        binding.offersToolbar.apply {
            setNavigationOnClickListener { requireActivity().onBackPressed() }
            setOnMenuItemClickListener {
                viewModel.showDetailsClicked()
                true
            }
        }

        groupieAdapter.add(activeSection)
        groupieAdapter.add(filterSection)
        groupieAdapter.add(offersSection)

        binding.offersRecycler.apply {
            adapter = groupieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.visibleOffersWithExperts.observe(viewLifecycleOwner, this::updateOffers)

        viewModel.wasFinished.observe(viewLifecycleOwner, this::updateResult)

        viewModel.isJobActive.observe(viewLifecycleOwner) { updateHeader() }
        viewModel.offersCount.observe(viewLifecycleOwner) { updateHeader() }
        viewModel.filterVisible.observe(viewLifecycleOwner, this::updateFilterVisibility)
        viewModel.visibleStatuses.observe(viewLifecycleOwner, this::updateFilterStatuses)
        viewModel.visibleStatuses.observe(viewLifecycleOwner) {
            Log.i("Hello", "Visible statuses: $it")
        }

        binding.offersRefreshLayout.setOnRefreshListener {
            binding.offersRefreshLayout.isRefreshing = false
            viewModel.refresh()
        }
    }

    private fun updateOffers(offers: List<OfferWithExpert>) {
        val newItems = offers.map { offer -> OfferWithExpertItem(offer, this) }
        offersSection.clear()
        offersSection.addAll(newItems)
    }

    private fun updateResult(wasFinished: Boolean) {
        val intent = Intent()
        intent.putExtra(RESULT_FINISHED, wasFinished)
        requireActivity().setResult(Activity.RESULT_OK, intent)
    }

    private fun updateHeader() {
        val offerActive = viewModel.isJobActive.value ?: false
        val offersCount = viewModel.offersCount.value ?: 0
        activeSection.clear()
        if(offerActive) {
            activeSection.add(JobActiveItem(this, viewModel.job.value?.finishDate, offersCount))
        }
        else {
            activeSection.add(JobFinishedItem())
        }
    }

    private fun updateFilterVisibility(visible: Boolean) {
        filterSection.clear()
        if(visible) {
            filterSection.add(statusFilterItem)
        }
    }

    private fun updateFilterStatuses(statuses: Set<OfferStatus>) {
        statusFilterItem.setSelectedStatuses(statuses)
    }

    override fun offerContentClicked(offerWithExpert: OfferWithExpert) {
        val intent = Intent(requireContext(), OfferActivity::class.java)
        val bundle = bundleOf(
            "offerId" to offerWithExpert.offer.id,
            "exit" to "slideRight",
        )
        intent.putExtras(bundle)
        startActivity(intent)
        Animatoo.animateSlideLeft(requireActivity())
    }

    override fun offerExpertClicked(offerWithExpert: OfferWithExpert, avatarView: AvatarView) {
        val expertUid = offerWithExpert.expert?.uid ?: return
        val intent = Intent(requireContext(), ProfileActivity::class.java)
        val bundle = bundleOf(
            "expertUid" to expertUid,
        )
        intent.putExtras(bundle)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            avatarView,
            "avatar"
        )
        startActivity(intent, options.toBundle())
    }

    override fun finishJobClicked() {
        showFinishQueryDialog()
    }

    override fun statusSelectionChanged(selected: Set<OfferStatus>) {
        viewModel.visibleStatusesChanged(selected)
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is OffersViewModel.Event.ShowJobDetails -> showJobDetails(event.jobId)
            is OffersViewModel.Event.ShowFinishJobFailure -> showFinishJobFailure(event.failure)
        }
    }

    private fun showJobDetails(jobId: String) {
        val intent = Intent(requireContext(), JobActivity::class.java)
        val bundle = bundleOf("jobId" to jobId)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun showFinishQueryDialog() {
        val dialog = YesNoDialogFragment.create(
            FINISH_JOB_ENSURE_DIALOG_KEY,
            "Czy na pewno chcesz zamknąć zlecenie?",)
        dialog.show(childFragmentManager, "YesNoDialogFragment")
    }

    private fun showFinishJobFailure(failure: Failure) {
        val dialog = FailureDialogFragment.createRetry(
            FINISH_JOB_FAILURE_DIALOG_KEY,
            "Zamknięcie zlecenia się nie powiodło.", failure)
        dialog.show(childFragmentManager, "FailureDialogFragment")
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }
}