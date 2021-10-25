package com.klima7.services.client.features.delete

import android.content.Intent
import com.klima7.services.client.R
import com.klima7.services.client.features.splash.SplashActivity
import com.klima7.services.common.components.delete.BaseDeleteFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeleteFragment: BaseDeleteFragment(
    R.string.delete_title,
    R.string.delete_text
) {

    override val viewModel: DeleteViewModel by viewModel()

    override fun showSplashScreen() {
        val intent = Intent(activity, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
        requireActivity().finish()
    }
}