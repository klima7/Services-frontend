package com.klima7.services.expert.features.info

import android.util.Log
import com.klima7.services.common.lib.failurable.FailurableFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class InfoContentFragment: FailurableFragment<FragmentInfoBinding>() {

    override val layoutId = R.layout.fragment_info
    override val viewModel: InfoContentViewModel by viewModel()

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.infoStarted()
    }

    override fun init() {
        super.init()

        viewModel.nameError.observe(viewLifecycleOwner) { nameError ->
            Log.i("Hello", "NameError received $nameError")
            binding.infoName.error = when(nameError) {
                null -> null
                NameError.NotProvided -> "To pole jest wymagane"
            }
        }

        viewModel.phoneError.observe(viewLifecycleOwner) { phoneError ->
            binding.infoPhone.error = when(phoneError) {
                null -> null
                PhoneError.TooShort -> "Numer za krótki"
            }
        }

        viewModel.emailError.observe(viewLifecycleOwner) { emailError ->
            binding.infoEmail.error = when(emailError) {
                null -> null
                EmailError.InvalidFormat -> "Nieprawidłowy format adresu email"
            }
        }

        viewModel.websiteError.observe(viewLifecycleOwner) { websiteError ->
            binding.infoWebsite.error = when(websiteError) {
                null -> null
                WebsiteError.InvalidFormat -> "Nieprawidłowy format adresu URL"
            }
        }

    }
}
