package com.klima7.services.expert.features.jobs.rejected

import com.klima7.services.expert.features.jobs.base.BaseJobsListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RejectedJobsListFragment: BaseJobsListFragment() {

    override val viewModel: RejectedJobsListViewModel by viewModel()

}