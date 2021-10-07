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

    override fun init() {
        super.init()
            lifecycleScope.launch {
            delay(2000)
            binding.infoName.error = "To pole jest wymagane"
            delay(2000)
            binding.infoName.error = null
        }

        viewModel.phoneError.observe(viewLifecycleOwner) { phoneError ->
            binding.infoPhone.error = when(phoneError) {
                null -> null
                InfoContentViewModel.PhoneError.TooShort -> "Numer za krótki"
            }
        }

        viewModel.emailError.observe(viewLifecycleOwner) { emailError ->
            binding.infoEmail.error = when(emailError) {
                null -> null
                InfoContentViewModel.EmailError.InvalidFormat -> "Nieprawidłowy format adresu"
            }
        }

//        binding.infoPhone.editText!!.addTextChangedListener(PhoneNumberFormattingTextWatcher())
    }
}
