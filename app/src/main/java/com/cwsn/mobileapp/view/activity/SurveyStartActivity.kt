package com.cwsn.mobileapp.view.activity

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.adapter.surveyQuestion.AllQuestionListAdapter
import com.cwsn.mobileapp.databinding.SurveySaveActivityBinding
import com.cwsn.mobileapp.local.table.MCQOptions
import com.cwsn.mobileapp.model.questions.LocalSurveyQuestion
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.Utils
import com.cwsn.mobileapp.utils.toast
import com.cwsn.mobileapp.view.activity.base.BaseActivity
import com.cwsn.mobileapp.view.callback.IQuestListInterface
import com.cwsn.mobileapp.viewmodel.survey.SurveyViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("MoveLambdaOutsideParentheses")
class SurveyStartActivity : BaseActivity<SurveySaveActivityBinding>() {
    private lateinit var allQuestAdapter: AllQuestionListAdapter
    private var schoolAddrs: String?=null
    private var schoolName: String?=null
    private var teacherId: String?=null
    private var schoolId: Int=0
    private val surveyViewModel by viewModel<SurveyViewModel>()
    private val appPreferences : AppPreferences by inject()
    private var allSurveyQuestOptions:MutableList<LocalSurveyQuestion> = mutableListOf()

    override fun getContext(): Context {
        return this@SurveyStartActivity
    }
    override fun inflateLayout(layoutInflater: LayoutInflater): SurveySaveActivityBinding {
        return SurveySaveActivityBinding.inflate(layoutInflater)
    }

    override fun isSetUpProgressDialog(): Boolean {
        return true
    }

    override fun onUserBackPressed() {
        finish()
    }

    override fun onActCreate() {
        getDataFromIntent()
        teacherId = appPreferences.getUserLoginData()[appPreferences.KEY_TEACHER_ID]
        binding.tvSchoolName.text=schoolName
        binding.tvSchoolAddress.text=schoolAddrs
        binding.surveyToolbar.imgGoBack.setOnClickListener {
            onUserBackPressed()
        }
    }

    private fun getDataFromIntent() {
        if(intent!=null){
            schoolId = intent.getIntExtra(Utils.SCHOOL_ID,0)
            schoolName=intent.getStringExtra(Utils.SCHOOL_NAME)
            schoolAddrs=intent.getStringExtra(Utils.SCHOOL_ADDRS)
        }
    }

    override fun onActStart() {

    }

    override fun onActPause() {

    }

    override fun onActResume() {
        surveyViewModel.getAllLocalDBQuestions().observe(this, { result->
            when(result.status){
                Status.SUCCESS->{
                    hideProgressDialog()
                    toast("question size ${result.data?.size}")
                    result.data?.let{quests->
                        for(allquest in quests){
                            allSurveyQuestOptions.add(LocalSurveyQuestion(allquest.serverQuestId,allquest.question,
                            allquest.questionFormat,allquest.questionType,allquest.fieldRequired,
                            null))
                        }
                        binding.rclyAllQuestion.apply {
                            layoutManager=LinearLayoutManager(context,RecyclerView.VERTICAL,false)
                            allQuestAdapter=AllQuestionListAdapter(allSurveyQuestOptions,object:IQuestListInterface{
                                override fun updateMCQOptionForId(quest: LocalSurveyQuestion) {
                                    fetchUpdateMCQOptionForQuestId(quest.serverQuestId,quest)
                                }
                            })
                            adapter=allQuestAdapter
                        }
                    }
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

    fun fetchUpdateMCQOptionForQuestId(questId:Int,question:LocalSurveyQuestion)
    {
        surveyViewModel.getAllMCQOptions(questId).observe(this, { result->
            when(result.status){
                Status.LOADING->{

                }
                Status.SUCCESS->{
                    result.data?.let{
                        toast("options size ${it.size}")
                        question.mcqOptionList=it as MutableList<MCQOptions>
                        Handler(Looper.getMainLooper()).postDelayed({
                            allQuestAdapter.refreshAdapter()
                        },1000)
                    }
                }
                Status.ERROR->{
                    result.message?.let{
                        toast(it)
                    }
                }
            }
        })
    }
    override fun onActStop() {

    }

}