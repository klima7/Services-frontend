package com.klima7.services.expert.features.jobs.base

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.klima7.services.common.models.ExpertJob

class JobsAdapter(
    private val onJobListener: OnJobListener
): PagingDataAdapter<ExpertJob, JobViewHolder>(JobComparator) {

    object JobComparator: DiffUtil.ItemCallback<ExpertJob>() {
        override fun areItemsTheSame(oldItem: ExpertJob, newItem: ExpertJob): Boolean {
            return oldItem.job.id == newItem.job.id
        }

        override fun areContentsTheSame(oldItem: ExpertJob, newItem: ExpertJob): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        return JobViewHolder(parent, onJobListener)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getExpertJob(position: Int): ExpertJob? {
        return getItem(position)
    }

    interface OnJobListener {
        fun onJobClicked(expertJob: ExpertJob);
    }

}

