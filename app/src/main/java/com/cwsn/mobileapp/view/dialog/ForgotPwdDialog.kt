package com.cwsn.mobileapp.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.DialogForgotPasswordLayoutBinding
import com.cwsn.mobileapp.utils.LoggerUtils
import com.cwsn.mobileapp.view.activity.LoginSignUpAct
import com.cwsn.mobileapp.view.callback.IForgetPwdCallback
import com.cwsn.mobileapp.viewmodel.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
Created by  on 01,July,2022
 **/
class ForgotPwdDialog :DialogFragment()


{
    val TAG: String =
        ForgotPwdDialog::class.java.getSimpleName()
    private var listener: IForgetPwdCallback? = null
    private var emailPattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private var usermail:String?=null

    private  val loginSignUpAct: LoginSignUpAct?=null
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
        try {
            if (context is IForgetPwdCallback) {
                listener = context
            }
        } catch (ex: Exception) {
            throw ClassCastException("$context must implement IForgetPwdCallback")

        }

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

        binding.txtResetPwd.setOnClickListener{
            if(validateRequiredField()) {
                //  loginViewModel.apply {  }
                //  loginViewModel.viewModelScope
                //   loginSignUpAct?.performForgotPassword(binding.edtEmailAddress.text.toString())
                if (listener != null) {
                    dismiss()
                    listener!!.performForgotPassword(binding.edtEmailAddress.text.toString())
//                loginViewModel.forgetPassword(binding.edtEmailAddress.text.toString())
                    LoggerUtils.error("email", binding.edtEmailAddress.text.toString())
                }
            }
        }

    }

    private fun getDataFromEditText(edtField: EditText): String {
        return edtField.text.toString().trim { it <= ' ' }
    }

    fun validateRequiredField(): Boolean {
        return if (getDataFromEditText(binding.edtEmailAddress).contains(".com")) {
            !!validateEmail(getDataFromEditText(binding.edtEmailAddress))!!
        } else {
            if (getDataFromEditText(binding.edtEmailAddress).isEmpty()) {
                showToastMessage("Email is required field")
                false
            } else {
                true
            }
        }
    }
    private fun validateEmail(email: String): Boolean? {
        val `value` = email
        return if (email.isEmpty()) {
            binding.edtEmailAddress.setError("Field cannot be empty")
            showToastMessage("Field cannot be empty")
            false

        }
//        else if (!value.matches(this.emailPattern)) {
//            binding.edtEmailAddress.setError("Invalid email address")
//            showToastMessage("Invalid email address")
//            false
//        }
        else {
            binding.edtEmailAddress.setError(null)
            true
        }
    }

    private fun showToastMessage(message: String) {
        val toast = Toast.makeText(activity, message, Toast.LENGTH_LONG)
        toast.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


}





