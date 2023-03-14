package com.cwsn.mobileapp.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.cwsn.mobileapp.databinding.DialogResetPasswordLayoutBinding
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.toast
import com.cwsn.mobileapp.view.callback.IChangePwdFragInterface
import com.cwsn.mobileapp.viewmodel.ChangePwd.ChangePwdVM
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
Created by  on 02,July,2022
 **/
class ResetPwdDialog:DialogFragment() ,IChangePwdFragInterface{
    private var _binding: DialogResetPasswordLayoutBinding? = null
    private val binding get() = _binding
    private var mInstance: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    var KEY_TOKEN = ""
    private var listener: IChangePwdFragInterface? = null
    private val changePwdModel by viewModel<ChangePwdVM>()
    private lateinit var mOldPassword: String
    private val appPref by inject<AppPreferences>()
    private var isFormValid: Boolean = false
    private var mRepeatNewPassword: String? = null
    private var mNewPassword: String = ""
    companion object {
        val TAG: String? = ResetPwdDialog::class.simpleName

        fun newInstance(): ResetPwdDialog {
            return ResetPwdDialog()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            if (context is IChangePwdFragInterface) {
                listener = context
            }
        } catch (ex: Exception) {
            throw ClassCastException("$context must implement IChangePwdFragInterface")

        }

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogResetPasswordLayoutBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        KEY_TOKEN = appPref.getUserLoginData()[appPref.KEY_TOKEN].toString()
        binding?.imgCloseDialog?.setOnClickListener {
            dismiss()
        }
        binding?.edtNewPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mNewPassword = s.toString()
                mNewPassword= binding!!.edtNewPassword.text.toString()
                mRepeatNewPassword= binding!!.edtRetypePassword.text.toString()

                if (mNewPassword.equals(mRepeatNewPassword, ignoreCase = true)) {
                  binding!!.tvRepeatPasswordError.visibility = View.INVISIBLE
                    isFormValid = true
                } else {
                    binding!!.tvRepeatPasswordError.visibility = View.VISIBLE
                    isFormValid = false
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        binding?.edtRetypePassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mRepeatNewPassword = s.toString()
                if (mRepeatNewPassword.equals(mNewPassword, ignoreCase = true)) {
                    binding!!.tvRepeatPasswordError.visibility = View.INVISIBLE
                    isFormValid = true
                } else {
                    binding!!.tvRepeatPasswordError.visibility = View.VISIBLE
                    isFormValid = false
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding?.txtResetPwd?.setOnClickListener {
            mOldPassword = binding!!.edtOldPassword.text.toString()
            fetchAccessToken()
            if(isFormValid && mOldPassword.isNotEmpty()) {
                listener?.onShowingProgress()
                changePwdModel.resetPassword(KEY_TOKEN,mNewPassword, mRepeatNewPassword!!).observe(this, Observer { userModel->
                   // listener?.hideProgress()
                    if(userModel.status.toString().equals(true))
                    {
                        activity?.toast(userModel.message.toString())
                        appPref.performAppLogout()
                    }
                    else{
                        activity?.toast(userModel.message.toString())
                    }
                })
            }

        }

        }
//    private fun changeUserPwd() {
//        listener?.onShowingProgress()
//        //val inputData = ChangePwdInput(mOldPassword, mNewPassword, mRepeatNewPassword)
//        mRepeatNewPassword?.let {
//            changePwdModel.resetPassword(mOldPassword, mNewPassword,
//                it
//            ).observe(this, Observer { userModel->
//                listener?.hideProgress()
//                if(userModel.status.equals(true){
//                    activity?.toast(userModel.responseMesg)
//                    appPref.performAppLogout()
//                } else{
//                    activity?.toast(userModel.responseMesg)
//                }
//            })
//        }



    fun toast(mesg: String, ctx: Context){
        Toast.makeText(ctx,mesg, Toast.LENGTH_LONG).show()
    }


    fun fetchAccessToken(): String? {
        return mInstance?.getString(KEY_TOKEN,"")
        KEY_TOKEN.toString();
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}