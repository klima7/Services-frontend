package com.klima7.services.common.lib.faildialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.klima7.services.common.R
import com.klima7.services.common.databinding.DialogFailureBinding
import com.klima7.services.common.domain.models.Failure
import com.klima7.services.common.lib.utils.FailureDescription
import org.koin.androidx.viewmodel.ext.android.viewModel


class FailureDialogFragment: DialogFragment() {

    enum class Result {
        DISMISS,
        RETRY
    }

    companion object {
        const val BUNDLE_KEY = "result"

        fun create(requestKey: String, message: String, failure: Failure, retryAbility: Boolean = true): FailureDialogFragment {
            val fragment = FailureDialogFragment()
            fragment.setData(requestKey, message, failure, retryAbility)
            return fragment
        }

    }

    private val viewModel: FailureDialogViewModel by viewModel()
    private lateinit var binding: DialogFailureBinding

    private lateinit var requestKey: String
    private lateinit var message: String
    private lateinit var failure: Failure
    private var retryAbility: Boolean = false

    private fun setData(requestKey: String, message: String, failure: Failure, retryAbility: Boolean) {
        this.requestKey = requestKey
        this.message = message
        this.failure = failure
        this.retryAbility = retryAbility
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if(savedInstanceState == null)
            viewModel.setData(requestKey, message, failure, retryAbility)

        val inflater = this.layoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_failure, null, false)

        val dialog: AlertDialog = MaterialAlertDialogBuilder(requireContext()).run {
            setNegativeButton("Anuluj") { _, _ ->
                val bundle = bundleOf("result" to Result.DISMISS)
                setFragmentResult(viewModel.requestKey, bundle)
            }
            if(retryAbility) {
                setPositiveButton("Ponów próbę") { _, _ ->
                    val bundle = bundleOf("result" to Result.RETRY)
                    setFragmentResult(viewModel.requestKey, bundle)
                }
            }
            background = ResourcesCompat.getDrawable(resources, R.drawable.shape_dialog_background,
                requireContext().theme)
            setView(binding.root)
            create()
        }


        initView()

        return dialog
    }

    private fun initView() {
        val desc = FailureDescription.get(viewModel.failure)
        binding.dialogFailureImage.setImageResource(desc.imageId)
        val completeMessage = "${resources.getString(desc.textId)}. ${viewModel.message}"
        binding.dialogFailureText.text = completeMessage
    }

}