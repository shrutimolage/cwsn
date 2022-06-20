package com.cwsn.mobileapp.view.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import com.cwsn.mobileapp.databinding.LoginSignupLayoutBinding
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.view.activity.base.BaseActivity
import com.cwsn.mobileapp.viewmodel.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("MoveLambdaOutsideParentheses")
class LoginSignUpAct : BaseActivity<LoginSignupLayoutBinding>()
{
    private val loginViewModel by viewModel<LoginViewModel>()

    override fun inflateLayout(layoutInflater: LayoutInflater): LoginSignupLayoutBinding {
        return LoginSignupLayoutBinding.inflate(layoutInflater)
    }

    override fun getContext(): Context {
        return this@LoginSignUpAct
    }

    override fun isToolBarEnable(): Boolean {
        return false
    }

    override fun onActStart() {

    }

    override fun onActCreate() {
        binding.tvBackBtn.visibility= View.INVISIBLE
        binding.tvActionLabel.text="LOGIN"
        binding.llActionBtn.setOnClickListener {
            loginViewModel.performUserLogin("test_teacher_1","paatham").observe(this, {
                when(it.status){
                    Status.SUCCESS->{
                        hideProgressDialog()
                    }
                    Status.ERROR->{
                        hideProgressDialog()
                    }
                    Status.LOADING->{
                        showProgressDialog()
                    }
                }
            })
        }
    }
}