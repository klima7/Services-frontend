package com.klima7.services.client.features.jobs

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.klima7.services.common.models.Job

class JobsAdapter(
    private val onJobListener: OnJobListener
): PagingDataAdapter<Job, JobViewHolder>(JobComparator) {

    object JobComparator: DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        return JobViewHolder(parent, onJobListener)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnJobListener {
        fun onJobClicked(job: Job)
    }

}

