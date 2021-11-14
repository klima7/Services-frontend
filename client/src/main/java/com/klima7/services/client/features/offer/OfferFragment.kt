package com.klima7.services.client.features.offer

import android.util.Log
import androidx.core.view.updateLayoutParams
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentOfferBinding
import com.klima7.services.common.platform.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class OfferFragment: BaseFragment<FragmentOfferBinding>() {

    override val layoutId = R.layout.fragment_offer
    override val viewModel: OfferViewModel by viewModel()

    override fun init() {
        val toolbar = binding.offerToolbar
        toolbar.setNavigationIcon(R.drawable.icon_arrow_back)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

//        setSendButtonSize()
    }

    private fun setSendButtonSize() {
        val size = binding.jobSendTextFrame.layoutParams.height
        Log.i("Hello", "size: $size")
        Log.i("Hello", "size2: ${binding.jobSendTextFrame.height}")
        Log.i("Hello", "size3: ${binding.jobSendTextFrame.measuredHeight}")
        Log.i("Hello", "size4: ${binding.jobSendTextFrame.measuredHeightAndState}")
        binding.jobSendButton.layoutParams.apply {
            height = size
            width = size
        }
        binding.jobSendButton.requestLayout()
    }

}