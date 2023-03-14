package com.cwsn.mobileapp.view.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.LoginSignupLayoutBinding
import com.cwsn.mobileapp.model.login.Data
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.toast
import com.cwsn.mobileapp.view.base.BaseActivity
import com.cwsn.mobileapp.view.callback.IForgetPwdCallback
import com.cwsn.mobileapp.view.dialog.ForgotPwdDialog
import com.cwsn.mobileapp.viewmodel.login.LoginViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("MoveLambdaOutsideParentheses")
class LoginSignUpAct : BaseActivity<LoginSignupLayoutBinding>(), IForgetPwdCallback {
    private var rememberMe: Boolean=false
    private val loginViewModel by viewModel<LoginViewModel>()
    private val appPref by inject<AppPreferences>()

    override fun inflateLayout(layoutInflater: LayoutInflater): LoginSignupLayoutBinding {
        return LoginSignupLayoutBinding.inflate(layoutInflater)
    }

    override fun getContext(): Context {
        return this@LoginSignUpAct
    }

    override fun isToolBarEnable(): Boolean {
        return false
    }

    override fun isSetUpProgressDialog(): Boolean {
        return true
    }

    override fun onActStart() {

    }

    override fun onActCreate() {
        binding.llActionBtn.setOnClickListener {
            if (validateField()) {
                userLogin()
            }
        }
        binding.tvForgotPwd.setOnClickListener{
            showForgotPwdDialog()
        }
        binding.cbSavePwd.setOnCheckedChangeListener(object:CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked){
                    rememberMe=true
                }
                else{
                    rememberMe=false
                    appPref.saveUserEmailAddress("")
                }
            }
        })
    }


    private fun showForgotPwdDialog() {
        val forgotPwdDialog=ForgotPwdDialog.newInstance()
        forgotPwdDialog.show(supportFragmentManager,ForgotPwdDialog.TAG)
    }

    private fun validateField(): Boolean {
        if(binding.etUsername.text.toString().isEmpty()){
            toast("Please enter email address")
            return false
        }
        else if(binding.etPassword.text.toString().isEmpty()){
            toast("Please enter password")
            return false
        }
        else{
            return true
        }
    }

    override fun onActResume() {
        val savedEmailAddress = appPref.getSavedEmailAddress()
        if(savedEmailAddress?.isNotEmpty()!!){
            binding.etUsername.setText(savedEmailAddress)
            binding.cbSavePwd.isChecked=true
        }
        val textLen = binding.tvForgotPwd.text.length
        val spannable = SpannableString(binding.tvForgotPwd.text)
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.mapCircleStrokeColor)),
            18,
            textLen,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.tvForgotPwd.text = spannable
    }

    private fun gotoDashboard() {
        val dashboardIntent = Intent(getContext(), AppDashboard::class.java)
        startActivity(dashboardIntent)
    }

    private fun userLogin() {
        loginViewModel.performUserLogin(binding.etUsername.text.toString(), binding.etPassword.text.toString()).observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    hideProgressDialog()
                    it.data?.body()?.let { loginData ->
                        loginData.message?.let {
                            toast(it)
                            if(rememberMe){
                                appPref.saveUserEmailAddress(binding.etUsername.text.toString())
                            }
                            else{
                                appPref.saveUserEmailAddress("")
                            }
                            gotoDashboard()
                        }
                    }
                }
                Status.ERROR -> {
                    hideProgressDialog()
                    it.message?.let {
                        toast(it)
                    }
                }
                Status.LOADING -> {
                    showProgressDialog()
                }
            }
        })
    }

    override fun performForgotPassword(emailOrUsername:String) {
        emailOrUsername.let { Data(it) }
        loginViewModel.forgetPassword(emailOrUsername).observe(this, {
            when (it.status) {
                Status.SUCCESS -> {

                    hideProgressDialog()
                    it.data?.body()?.let { forgot ->
                        forgot.message?.let {
                            toast(it)
                        }
                    }
                }
                Status.ERROR -> {
                    hideProgressDialog()
                    it.message?.let {
                        toast(it)
                    }
                }
                Status.LOADING -> {
                    showProgressDialog()
                }
            }
        })
    }
}