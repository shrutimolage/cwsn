package com.cwsn.mobileapp.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.DialogForgotPasswordLayoutBinding

/**
Created by  on 01,July,2022
 **/
class ForgotPwdDialog :DialogFragment()
{
    private var _binding:DialogForgotPasswordLayoutBinding?=null
    private val binding get() = _binding!!
    companion object{
        val TAG: String? =ForgotPwdDialog::class.simpleName

        fun newInstance(): ForgotPwdDialog {
            return ForgotPwdDialog()
        }
    }
   
    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawableResource(R.color.semiTransparentColor)
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= DialogForgotPasswordLayoutBinding.inflate(inflater,container,false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgCloseDialog.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}