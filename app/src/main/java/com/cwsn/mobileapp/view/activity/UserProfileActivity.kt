package com.cwsn.mobileapp.view.activity

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.ActivityUserProfileBinding
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.toast
import com.cwsn.mobileapp.view.base.BaseActivity
import com.cwsn.mobileapp.viewmodel.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("MoveLambdaOutsideParentheses")
class UserProfileActivity : BaseActivity<ActivityUserProfileBinding>() {

    private val profileViewModel by viewModel<ProfileViewModel>()

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

    override fun getToolBarTitleView(): TextView? {
        return findViewById(R.id.toolbar_title)
    }

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

    }

    override fun onActPause() {

    }

    override fun onActResume() {
        profileViewModel.getUserProfile().observe(this, { profileData->
            when(profileData.status){
                Status.SUCCESS -> {
                    hideProgressDialog()
                    profileData.data?.body()?.user?.let {userData ->
                        binding.tvTeacherName.text=userData.name
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

    override fun onActStop() {

    }

}