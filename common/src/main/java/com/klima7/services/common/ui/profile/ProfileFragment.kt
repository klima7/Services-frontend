package com.klima7.services.common.ui.profile

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentProfileBinding
import com.klima7.services.common.ui.base.BaseLoadFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment: BaseLoadFragment<FragmentProfileBinding>() {

    override val layoutId = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModel()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun init() {
        super.init()

        viewModel.expert.observe(viewLifecycleOwner) { expert ->
            binding.infoAvatarView.setProfileImage(expert.profileImage)
        }
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.start()
        Log.i("Hello", "First creation")
    }

}