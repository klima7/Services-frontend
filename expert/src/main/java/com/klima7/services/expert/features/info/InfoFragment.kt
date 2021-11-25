package com.klima7.services.expert.features.info

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.PickImageContract
import com.canhub.cropper.options
import com.klima7.services.common.components.faildialog.FailureDialogFragment
import com.klima7.services.common.models.Failure
import com.klima7.services.common.platform.BaseLoadFragment
import com.klima7.services.common.platform.BaseViewModel
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class InfoFragment: BaseLoadFragment<FragmentInfoBinding>() {

    override val layoutId = R.layout.fragment_info
    override val viewModel: InfoViewModel by viewModel()

    companion object {
        const val SAVE_INFO_FAILURE_KEY = "SAVE_INFO_FAILURE_KEY"
    }

    private val imagePickerLauncher = registerForActivityResult(PickImageContract()) {
        receiveProfileImagePickerResult(it)
    }

    private val profileImageCropperLauncher = registerForActivityResult(CropImageContract()) {
        receiveProfileImageCropperResult(it)
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        viewModel.started()
    }

    override fun init() {
        super.init()

        childFragmentManager.setFragmentResultListener(SAVE_INFO_FAILURE_KEY, viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(FailureDialogFragment.BUNDLE_KEY)
            if(result == FailureDialogFragment.Result.RETRY) {
                viewModel.retrySaveClicked()
            }
        }

        viewModel.nameError.observe(viewLifecycleOwner) { nameError ->
            binding.infoName.error = when(nameError) {
                null -> null
                InfoFormErrors.NameError.NotProvided -> "To pole jest wymagane"
            }
        }

        viewModel.phoneError.observe(viewLifecycleOwner) { phoneError ->
            binding.infoPhone.error = when(phoneError) {
                null -> null
                InfoFormErrors.PhoneError.TooShort -> "Numer za krótki"
            }
        }

        viewModel.emailError.observe(viewLifecycleOwner) { emailError ->
            binding.infoEmail.error = when(emailError) {
                null -> null
                InfoFormErrors.EmailError.InvalidFormat -> "Nieprawidłowy format adresu email"
            }
        }

        viewModel.websiteError.observe(viewLifecycleOwner) { websiteError ->
            binding.infoWebsite.error = when(websiteError) {
                null -> null
                InfoFormErrors.WebsiteError.InvalidFormat -> "Nieprawidłowy format adresu URL"
            }
        }

        viewModel.avatar.observe(viewLifecycleOwner) { avatar ->
            binding.infoAvatarView.setProfileImage(avatar)
        }

    }

    override suspend fun handleEvent(event: BaseViewModel.BaseEvent) {
        super.handleEvent(event)
        when(event) {
            InfoViewModel.Event.FinishInfo -> finishInfo()
            InfoViewModel.Event.StartProfileImagePicker -> startProfileImagePicker()
            is InfoViewModel.Event.ShowSaveFailure -> showSaveError(event.failure)
        }
    }

    private fun finishInfo() {
        showToastSuccess("Profil został zaktualizowany")
        requireActivity().finish()
    }

    private fun startProfileImagePicker() {
        imagePickerLauncher.launch(false)
    }

    private fun receiveProfileImagePickerResult(uri: Uri?) {
        if(uri != null) {
            startProfileImageCropper(uri)
        }
        // Else pick image error
    }

    private fun startProfileImageCropper(uri: Uri) {
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
                result.uriContent?.let {
                    viewModel.profileImageSelected(it.toString())
                }
            }
            // May be also checked against cancelled and error
        }
    }

    private fun showSaveError(failure: Failure) {
        val dialog = FailureDialogFragment.createRetry(
            SAVE_INFO_FAILURE_KEY,
            "Zmiana profilu się nie powiodła.", failure)
        dialog.show(childFragmentManager, "FailureDialogFragment")
    }

}
