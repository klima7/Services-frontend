package com.klima7.services.expert.features.jobs.new

import android.graphics.Canvas
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.klima7.services.common.components.faildialog.FailureDialogFragment
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.features.jobs.JobsViewModel
import com.klima7.services.expert.features.jobs.base.BaseJobsListFragment
import com.klima7.services.expert.features.services.ServicesFragment
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewJobsListFragment: BaseJobsListFragment() {

    companion object {
        const val DIALOG_REJECT_FAILURE_KEY = "DIALOG_REJECT_FAILURE_KEY"
    }

    override val viewModel: NewJobsListViewModel by viewModel()
    private val parentViewModel: JobsViewModel by lazy {
        requireParentFragment().requireParentFragment().getViewModel()
    }

    override fun init() {
        super.init()
        attachItemTouchHelper(ItemTouchHelper(itemTouchHelperCallback))

        childFragmentManager.setFragmentResultListener(DIALOG_REJECT_FAILURE_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(FailureDialogFragment.BUNDLE_KEY)
            if(result == FailureDialogFragment.Result.RETRY) {
                viewModel.retryReject()
            }
        }
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is NewJobsListViewModel.Event.ShowRejectFailure -> showRejectFailure(event.failure)
        }
    }

    private fun showRejectFailure(failure: Failure) {
        val dialog = FailureDialogFragment.createRetry(
            DIALOG_REJECT_FAILURE_KEY,
            requireContext().getString(R.string.job__reject_failure_message),
            failure
        )
        dialog.show(childFragmentManager, ServicesFragment.SAVE_FAILURE_KEY)
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
                val expertJob = jobsAdapter.getExpertJob(position)
                if (expertJob != null) {
                    viewModel.jobSwiped(expertJob.job.id)
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
                            R.color.swipe_reject_job_background
                        )
                    )
                    .addActionIcon(R.drawable.icon_jobs_rejected)
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

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                val swiping = actionState == ItemTouchHelper.ACTION_STATE_SWIPE
                parentViewModel.setRefreshEnabled(!swiping)
            }
        }

}