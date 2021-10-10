package com.klima7.services.expert.features.services

import com.klima7.services.common.domain.models.Service
import com.klima7.services.common.lib.failurable.FailurableFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentServicesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ServicesContentFragment: FailurableFragment<FragmentServicesBinding>() {

    override val layoutId = R.layout.fragment_services
    override val viewModel: ServicesContentViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.servicesStarted()

        val fragment = childFragmentManager.findFragmentById(R.id.services_category) as ServicesCategoryFragment
        fragment.setName("Hydraulik")
        fragment.setServices(
            listOf(
                Service("1", "abc", "2"),
                Service("1", "abc", "2"),
                Service("1", "abc", "2"),
                Service("1", "abc", "2"),
                Service("1", "abc", "2"),
                Service("1", "abc", "2"),
                Service("1", "abc", "2"),
            )
        )

        val fragment2 = childFragmentManager.findFragmentById(R.id.services_category2) as ServicesCategoryFragment
        fragment2.setName("Elektryk")
        fragment2.setServices(
            listOf(
                Service("1", "Wymiana instalacji elektrycznej", "2"),
                Service("1", "Wymiana instalacji elektrycznej", "2"),
                Service("1", "Wymiana instalacji elektrycznej", "2"),
                Service("1", "Wymiana instalacji elektrycznej", "2"),
                Service("1", "Wymiana instalacji elektrycznej", "2"),
            )
        )
    }

}
