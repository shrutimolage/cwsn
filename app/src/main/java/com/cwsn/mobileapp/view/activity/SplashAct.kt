package com.cwsn.mobileapp.view.activity

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import com.cwsn.mobileapp.databinding.ActivitySplashBinding
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.view.activity.base.BaseActivity

class SplashAct : BaseActivity<ActivitySplashBinding>() {

    private val appPreferences by lazy { AppPreferences(this) }

    override fun getContext(): Context {
        return this@SplashAct
    }

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onActStart() {

    }

    override fun onActCreate() {
        Handler(Looper.getMainLooper()).postDelayed({
            appPreferences.getUserLoginStatus()?.let { loginStatus->
                if(loginStatus){
                    gotoDashboard()
                }
                else{
                    gotoLoginScreen()
                }
            }
        },3000)

    }

    private fun gotoDashboard() {
        val dashboard= Intent(getContext(),AppDashboard::class.java)
        dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(dashboard)
        finish()
    }

    private fun gotoLoginScreen(){
        val loginScreen=Intent(getContext(),LoginSignUpAct::class.java)
        loginScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(loginScreen)
        finish()
    }

    override fun onActResume() {

    }

    override fun onActPause() {

    }

    override fun onActStop() {

    }
}