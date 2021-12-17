package com.klima7.services.expert.features.delete

import android.content.Intent
import com.klima7.services.common.components.delete.BaseDeleteFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.features.splash.SplashActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DeleteFragment: BaseDeleteFragment(
    R.string.delete__title,
    R.string.delete__text
) {

    override val viewModel: DeleteViewModel by viewModel()

    override fun showSplashScreen() {
        val intent = Intent(activity, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME
        startActivity(intent)
        requireActivity().finish()
    }
}