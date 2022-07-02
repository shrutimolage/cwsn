package com.cwsn.mobileapp.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.cwsn.mobileapp.databinding.DialogResetPasswordLayoutBinding

/**
Created by  on 02,July,2022
 **/
class ResetPwdDialog:DialogFragment()
{
    private var _binding:DialogResetPasswordLayoutBinding?=null
    private val binding get()=_binding
    companion object{
        val TAG: String? =ResetPwdDialog::class.simpleName

        fun newInstance(): ResetPwdDialog {
            return ResetPwdDialog()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }



}