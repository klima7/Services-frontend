package com.klima7.services.expert.features.info

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.common.lib.failurable.FailurableFragment
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.canhub.cropper.*


class InfoContentFragment: FailurableFragment<FragmentInfoBinding>() {

    override val layoutId = R.layout.fragment_info
    override val viewModel: InfoContentViewModel by viewModel()

    private val imagePickerLauncher = registerForActivityResult(PickImageContract()) {
        receiveProfileImagePickerResult(it)
    }

    private val profileImageCropperLauncher = registerForActivityResult(CropImageContract()) {
        receiveProfileImageCropperResult(it)
    }

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

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            InfoContentViewModel.Event.FinishInfo -> finishInfo()
            InfoContentViewModel.Event.StartProfileImagePicker -> startProfileImagePicker()
        }
    }

    private fun finishInfo() {
        Toast.makeText(requireContext(), "Profil został zaktualizowany", Toast.LENGTH_SHORT).show()
        requireActivity().finish()
    }

    private fun startProfileImagePicker() {
        imagePickerLauncher.launch(false)
    }

    private fun receiveProfileImagePickerResult(uri: Uri?) {
        if(uri != null) {
            Log.i("Hello", "Selected image: $uri")
            startProfileImageCropper(uri)
        }
        else {
            Log.i(TAG, "Pick image error")
        }
    }

    private fun startProfileImageCropper(uri: Uri) {
        Log.i(TAG, "Starting cropper")
        profileImageCropperLauncher.launch(
            options(uri = uri) {
                setGuidelines(CropImageView.Guidelines.ON)
                setOutputCompressFormat(Bitmap.CompressFormat.PNG)
                setAspectRatio(1, 1)
                setCropShape(CropImageView.CropShape.OVAL)
                setRequestedSize(256, 256)
            }
        )
    }

    private fun receiveProfileImageCropperResult(result: CropImageView.CropResult) {
        when {
            result.isSuccessful -> {
                Log.i(TAG, "result:" + result.uriContent)
                result.uriContent?.let {
                    viewModel.profileImageSelected(it.toString())
                }
            }
            result is CropImage.CancelledResult -> {
                Log.e(TAG, "Cropping cancelled")
            }
            else -> {
                Log.e(TAG, "Cropping error")
            }
        }
    }

}
