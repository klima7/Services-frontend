package com.klima7.services.common.components.profile.contact

import android.content.Intent
import android.net.Uri
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileContactBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileContactFragment: BaseFragment<FragmentProfileContactBinding>() {

    override val layoutId = R.layout.fragment_profile_contact
    override val viewModel: ProfileContactViewModel by viewModel()

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            is ProfileContactViewModel.Event.Call -> call(event.phoneNumber)
            is ProfileContactViewModel.Event.SendEmail -> sendEmail(event.emailAddress)
            is ProfileContactViewModel.Event.OpenWebsite -> openWebsite(event.url)
        }
    }

    private fun call(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:$phoneNumber"))
        startActivity(intent)
    }

    private fun sendEmail(emailAddress: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:$emailAddress"))
        startActivity(intent)
    }

    private fun openWebsite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}