package com.example.omapp.common.presentation

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.omapp.R

const val ERROR_MESSAGE_KEY = "ErrorMessageKey"
class ErrorDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            return AlertDialog.Builder(it)
                .setMessage(
                    getString(
                        R.string.error_message,
                        arguments?.getString(ERROR_MESSAGE_KEY)
                    )
                )
                .setNegativeButton(R.string.dismiss) { _, _ -> dismiss() }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}