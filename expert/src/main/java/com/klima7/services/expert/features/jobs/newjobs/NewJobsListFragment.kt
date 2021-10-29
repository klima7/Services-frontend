package com.klima7.services.expert.features.jobs.newjobs

import com.klima7.services.expert.features.jobs.base.BaseJobsListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewJobsListFragment: BaseJobsListFragment() {

    override val viewModel: NewJobsListViewModel by viewModel()

}