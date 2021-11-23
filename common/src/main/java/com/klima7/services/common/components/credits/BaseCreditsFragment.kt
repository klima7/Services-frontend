package com.klima7.services.common.components.credits

import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentCreditsBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.groupiex.plusAssign

abstract class BaseCreditsFragment: BaseFragment<FragmentCreditsBinding>() {

    override val layoutId = R.layout.fragment_credits
    override val viewModel = BaseViewModel()

    private val adapter = GroupieAdapter()

    override fun init() {
        super.init()

        binding.creditsToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        val recycler = binding.creditsRecycler
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        adapter += CreditsHeadingItem()
        for(credit in getCredits()) {
            adapter += CreditItem(credit)
        }
    }

    private fun getCredits(): List<Credit> {
        return getCommonCredits() + getSpecificCredits()
    }

    private fun getCommonCredits(): List<Credit> {
        return listOf(
            Credit(R.drawable.pin_red, R.string.credit_pin_red),
        )
    }

    abstract fun getSpecificCredits(): List<Credit>
}