package com.cwsn.mobileapp.view.activity

import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.cwsn.mobileapp.databinding.SurveySaveActivityBinding
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.Utils
import com.cwsn.mobileapp.utils.toast
import com.cwsn.mobileapp.view.activity.base.BaseActivity
import com.cwsn.mobileapp.viewmodel.survey.SurveyViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SurveyStartActivity : BaseActivity<SurveySaveActivityBinding>() {
    private var teacherId: String?=null
    private var schoolId: Int=0
    private val surveyViewModel by viewModel<SurveyViewModel>()
    private val appPreferences : AppPreferences by inject()

    override fun getContext(): Context {
        return this@SurveyStartActivity
    }
    override fun inflateLayout(layoutInflater: LayoutInflater): SurveySaveActivityBinding {
        return SurveySaveActivityBinding.inflate(layoutInflater)
    }

    override fun isSetUpProgressDialog(): Boolean {
        return true
    }

    override fun onActCreate() {
        getDataFromIntent()
        teacherId = appPreferences.getUserLoginData()[appPreferences.KEY_TEACHER_ID]
        toast("teacherId $teacherId")
    }

    private fun getDataFromIntent() {
        if(intent!=null){
            schoolId = intent.getIntExtra(Utils.SCHOOL_ID,0)
        }
    }

    override fun onActStart() {

    }

    override fun onActPause() {

    }

    override fun onActResume() {
        surveyViewModel.getAllLocalDBQuestions().observe(this, Observer { result->
            when(result.status){
                Status.SUCCESS->{
                    hideProgressDialog()
                    toast("question size ${result.data?.size}")
                }
                Status.ERROR->{
                    hideProgressDialog()
                    result.message?.let {
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