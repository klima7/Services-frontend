package com.klima7.services.expert.features.info

import androidx.lifecycle.lifecycleScope
import com.klima7.services.common.lib.failurable.FailurableFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentInfoBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class InfoContentFragment: FailurableFragment<FragmentInfoBinding>() {

    override val layoutId = R.layout.fragment_info
    override val viewModel: InfoContentViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.doSomething()
    }

    init {
        lifecycleScope.launch {
            delay(2000)
            binding.infoName.error = "To pole jest wymagane"
            binding.infoPhone.error = "To pole jest wymagane"
        }
    }
}
