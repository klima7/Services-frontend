package com.klima7.services.client.features.setup

import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentSetupBinding
import com.klima7.services.client.features.info.InfoActivity
import com.klima7.services.common.platform.BaseLoadFragment
import com.klima7.services.common.platform.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SetupFragment: BaseLoadFragment<FragmentSetupBinding>() {

    override val layoutId = R.layout.fragment_setup
    override val viewModel: SetupViewModel by viewModel()

    private val configLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        this.onConfigDone()
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.started()
    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            SetupViewModel.Event.ShowHomeScreen -> showHomeScreen()
            SetupViewModel.Event.ShowInfoScreen -> showInfoScreen()
        }
    }

    private fun showHomeScreen() {
//        val intent = Intent(activity, HomeActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
//        startActivity(intent)
//        requireActivity().finish()
    }

    private fun showInfoScreen() {
        val intent = Intent(activity, InfoActivity::class.java)
        configLauncher.launch(intent)
    }

    private fun onConfigDone() {
//        viewModel.configDone()
    }

}