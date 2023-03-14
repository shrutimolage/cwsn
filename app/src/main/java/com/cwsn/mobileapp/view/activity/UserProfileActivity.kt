package com.cwsn.mobileapp.view.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.ActivityUserProfileBinding
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.LoggerUtils
import com.cwsn.mobileapp.utils.toast
import com.cwsn.mobileapp.view.base.BaseActivity
import com.cwsn.mobileapp.view.callback.IChangePwdFragInterface
import com.cwsn.mobileapp.view.dialog.ForgotPwdDialog
import com.cwsn.mobileapp.view.dialog.ResetPwdDialog
import com.cwsn.mobileapp.viewmodel.profile.ProfileViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("MoveLambdaOutsideParentheses")
class UserProfileActivity : BaseActivity<ActivityUserProfileBinding>(),IChangePwdFragInterface{
    private val appPref by inject<AppPreferences>()
    private val profileViewModel by viewModel<ProfileViewModel>()
    private var mToken:String?=null
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityUserProfileBinding {
        return ActivityUserProfileBinding.inflate(layoutInflater)
    }

    override fun getContext(): Context {
        return this@UserProfileActivity
    }

    override fun isToolBarEnable(): Boolean {
        return true
    }

    override fun getToolBar(): Toolbar? {
        return findViewById(R.id.toolbar)
    }

//    override fun getToolBarTitleView(): TextView? {
//        return findViewById(R.id.toolbar_title)
//    }

    override fun getToolBartTitle(): String {
        return "Profile"
    }

    override fun isBackArrowEnabled(): Boolean {
        return false
    }

    override fun getToolBarBackArrowView(): ImageView? {
        return findViewById(R.id.navigationBar)
    }

    override fun onUserBackPressed() {
        finish()
    }

    override fun onToolbarBackArrowPress() {
        finish()
    }

    override fun isSetUpProgressDialog(): Boolean {
        return true
    }

    override fun onActStart() {

    }

    override fun onActCreate() {
        mToken=appPref.KEY_TOKEN
        LoggerUtils.error("TOKEN",mToken)
        binding.resetpassword.setOnClickListener {

           showResetPwdDialog()
        }
    }

    override fun onActPause() {

    }

    override fun onActResume() {

        profileViewModel.getUserProfile().observe(this, { profileData->
            when(profileData.status){
                Status.SUCCESS -> {
                    hideProgressDialog()
                    profileData.data?.body()?.user?.let { userData ->
                        binding.tvTeacherName.setText(userData.name)
                        binding.edtDob.setText(userData.email)
                        binding.edtUserName.setText(userData.name)
                        binding.edtGender.setText(userData.gender)
                        binding.edtAddress.setText(userData.aadhaar_number)
                        binding.edtCityName.setText(userData.whatsapp_number)
                        binding.edtPincode.setText(userData.user_role)

                        // LoggerUtils.error("userdata",userData.user_address)
                        // binding.edtCityName.setText(userData.user_address)
//                        binding.
                    }
                }
                Status.ERROR->{
                    hideProgressDialog()
                    profileData.message?.let {
                        toast(it)
                    }

                }
                Status.LOADING->{
                    showProgressDialog()
                }
            }
        })
    }

    private fun showResetPwdDialog() {
        val ResetpasswordDialog= ResetPwdDialog.newInstance()
        ResetpasswordDialog .show(supportFragmentManager, ResetPwdDialog.TAG)
    }

    override fun onActStop() {

    }

}