package com.klima7.services.common.components.yesnodialog

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
import com.klima7.services.common.databinding.DialogYesNoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class YesNoDialogFragment: DialogFragment() {

    enum class Result {
        NO,
        YES
    }

    companion object {

        const val BUNDLE_KEY = "result"

        fun create(requestKey: String, message: String): YesNoDialogFragment {
            val fragment = YesNoDialogFragment()
            fragment.setData(requestKey, message)
            return fragment
        }
    }

    private val viewModel: YesNoDialogViewModel by viewModel()
    private lateinit var binding: DialogYesNoBinding

    private lateinit var requestKey: String
    private lateinit var message: String

    private fun setData(requestKey: String, message: String) {
        this.requestKey = requestKey
        this.message = message
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if(savedInstanceState == null)
            viewModel.setData(requestKey, message)

        val inflater = this.layoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_yes_no, null, false)

        val dialog: AlertDialog = MaterialAlertDialogBuilder(requireContext()).run {
            setNegativeButton(requireContext().getString(R.string.dialog__no)) { _, _ ->
                val bundle = bundleOf("result" to Result.NO)
                setFragmentResult(viewModel.requestKey, bundle)
            }
            setPositiveButton(requireContext().getString(R.string.dialog__yes)) { _, _ ->
                val bundle = bundleOf("result" to Result.YES)
                setFragmentResult(viewModel.requestKey, bundle)
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
        binding.yesnodialogMessage.text = viewModel.message
    }

}