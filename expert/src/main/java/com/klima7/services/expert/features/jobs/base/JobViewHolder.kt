package com.klima7.services.expert.features.jobs.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.klima7.services.common.R
import com.klima7.services.common.databinding.ElementJobBinding
import com.klima7.services.common.models.Job

class JobViewHolder private constructor(
    private val binding: ElementJobBinding,
    private val onJobListener: JobsAdapter.OnJobListener):
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        operator fun invoke(parent: ViewGroup, onJobListener: JobsAdapter.OnJobListener): JobViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding: ElementJobBinding = DataBindingUtil.inflate(inflater,
                R.layout.element_job, parent, false)
            return JobViewHolder(binding, onJobListener)
        }
    }

    fun bind(job: Job?) {
        if(job == null)
            return

        binding.job = job
        binding.jobelemFrame.setOnClickListener {
            onJobListener.onJobClicked(job)
        }
    }

}