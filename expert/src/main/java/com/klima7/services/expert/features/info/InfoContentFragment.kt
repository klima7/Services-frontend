package com.klima7.services.expert.features.info

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.PickImageContract
import com.canhub.cropper.options
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.base.BaseViewModel
import com.klima7.services.common.lib.faildialog.FailureDialogFragment
import com.klima7.services.common.lib.failfrag.FailurableFragment
import com.klima7.services.common.lib.utils.showShortToast
import com.klima7.services.expert.R
import com.klima7.services.expert.databinding.FragmentInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


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

        childFragmentManager.setFragmentResultListener("key", viewLifecycleOwner) { _: String, bundle: Bundle ->
            val result = bundle.get(FailureDialogFragment.BUNDLE_KEY)
            Log.i("Hello", "Result: $result (retry=${result==FailureDialogFragment.Result.RETRY})")
        }

        viewModel.nameError.observe(viewLifecycleOwner) { nameError ->
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
            InfoContentViewModel.Event.StartProfileImagePicker -> showDialog()
            InfoContentViewModel.Event.ShowSaveError -> showSaveError()
        }
    }

    private fun showDialog() {
        val dialog = FailureDialogFragment.create("key", "Aktualizacja profilu się nie powiodła.", Failure.NotFoundFailure)
        dialog.show(childFragmentManager, "FailureDialogFragment")
    }

    private fun finishInfo() {
        showShortToast("Profil został zaktualizowany")
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

    private fun showSaveError() {
        Toast.makeText(requireContext(), "Zapisywanie profilu się nie powiodło", Toast.LENGTH_SHORT).show()
    }

}
