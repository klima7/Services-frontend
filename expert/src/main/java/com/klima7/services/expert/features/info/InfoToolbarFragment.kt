package com.klima7.services.expert.features.info

import com.klima7.services.common.ui.base.BaseFragment
import com.klima7.services.common.ui.utils.replaceFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentToolbarBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoToolbarFragment: BaseFragment<FragmentToolbarBinding>() {

    override val layoutId = R.layout.fragment_toolbar
    override val viewModel: InfoViewModel by viewModel()

    override fun init() {
        super.init()
        binding.toolbarToolbar.apply {
            title = "Zmiana profilu"
            setNavigationIcon(R.drawable.icon_arrow_back)
            setNavigationOnClickListener { requireActivity().onBackPressed() }
        }

        replaceFragment(R.id.toolbar_container_view, InfoFragment())
    }
}