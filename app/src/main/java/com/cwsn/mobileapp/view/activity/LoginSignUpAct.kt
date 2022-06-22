package com.cwsn.mobileapp.view.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import com.cwsn.mobileapp.databinding.LoginSignupLayoutBinding
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.toast
import com.cwsn.mobileapp.view.activity.base.BaseActivity
import com.cwsn.mobileapp.viewmodel.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("MoveLambdaOutsideParentheses")
class LoginSignUpAct : BaseActivity<LoginSignupLayoutBinding>()
{
    private val loginViewModel by viewModel<LoginViewModel>()
    private val appPreferences by lazy { AppPreferences(this) }

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
        binding.tvBackBtn.visibility= View.INVISIBLE
        binding.llActionBtn.setOnClickListener {
            gotoDashboard()
            //userLogin()
        }
    }

    private fun gotoDashboard() {
        val dashboardIntent= Intent(getContext(),AppDashboard::class.java)
        startActivity(dashboardIntent)
    }

    private fun userLogin() {
        loginViewModel.performUserLogin("test_teacher_1", "paatham").observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    hideProgressDialog()
                    it.data?.body()?.let { loginData ->
                        loginData.msg?.let {
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