package com.cwsn.mobileapp.view.activity

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.ActivityQuestionBinding
import com.cwsn.mobileapp.databinding.ActivitySchoolTypeBinding
import com.cwsn.mobileapp.model.questions.QuestListInput
import com.cwsn.mobileapp.model.questions.QuestionData
import com.cwsn.mobileapp.model.survey.SurveyInput
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.LoggerUtils
import com.cwsn.mobileapp.utils.Utils
import com.cwsn.mobileapp.utils.toast
import com.cwsn.mobileapp.view.adapter.QuestionListAdapter
import com.cwsn.mobileapp.view.base.BaseActivity
import com.cwsn.mobileapp.view.callback.IQuestListInterface
import com.cwsn.mobileapp.view.callback.IQuestionListCallback
import com.cwsn.mobileapp.view.dialog.ResetPwdDialog
import com.cwsn.mobileapp.view.fragment.HomeFragment
import com.cwsn.mobileapp.viewmodel.survey.SurveyViewModel
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import org.koin.android.ext.android.inject
import java.lang.Exception
import java.lang.reflect.Array.getInt

class QuestionActivity : BaseActivity<ActivityQuestionBinding>(), IQuestionListCallback {
    private var locationAddress: String? = null
    private var teacherId: String? = null
    private lateinit var questAdapter: QuestionListAdapter
    private var schoolAddress: String? = null
    private var block_name: String? = null
    private var district_name: String? = null
    private var district_id: Int? = null
    private var homeFragment:HomeFragment?=null
    private var school_id: Int? = null
    private var schoolName: String? = null
    private var block_id: Int? = null
    private var cluster_id: Int? = null
    private var blo: Int? = null
    private var formId: Int? =null
    private var formatId: Int? = 0
    private val viewModel by inject<SurveyViewModel>()
    private var listener: IQuestionListCallback? = null
    private var updateQuestionList: MutableList<QuestionData> = mutableListOf()
    private var surveyRequest: MutableList<SurveyInput> = mutableListOf()
    private val appPref by inject<AppPreferences>()
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityQuestionBinding {
        return ActivityQuestionBinding.inflate(layoutInflater)
    }

    override fun getContext(): Context {
        return this@QuestionActivity
    }

    override fun isToolBarEnable(): Boolean {
        return true
    }

    override fun getToolBar(): Toolbar? {
        return findViewById(R.id.toolbar)
    }


    override fun getToolBartTitle(): String {
        return "Survey"
    }

    override fun isBackArrowEnabled(): Boolean {
        return true
    }

    override fun getToolBarBackArrowView(): ImageView? {
        return findViewById(R.id.navigationBar)
    }

    override fun onUserBackPressed() {
        finish()
    }

    override fun showProgress() {
        showProgressDialog()
    }

    override fun hideProgress() {
       hideProgressDialog()
    }

    override fun onToolbarBackArrowPress() {
    }

    override fun isSetUpProgressDialog(): Boolean {
        return true
    }

    override fun onActStart() {

        try {
            if (context is IQuestionListCallback) {
                listener = this
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ClassCastException(
                context.toString()
                        + " must implement IQuestionListCallback"
            )
        }

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                listener?.onUserBackPressed()
            }
        }
        QuestionActivity().onBackPressedDispatcher.addCallback(callback)
    }


    @SuppressLint("SetTextI18n")
    override fun onActCreate() {

        teacherId = appPref.getUserLoginData()[appPref.KEY_TEACHER_ID]
        block_id = appPref.getUserLoginData()[appPref.KEY_BLOCK_ID]?.toInt()
        cluster_id = appPref.getUserLoginData()[appPref.KEY_CLUSTER_ID]?.toInt()
        block_name = appPref.getUserLoginData()[appPref.KEY_BLOCK_NAME]
        district_name = appPref.getUserLoginData()[appPref.KEY_DISTRICT_NAME]
        district_id = appPref.getUserLoginData()[appPref.KEY_DISTRICT_ID]?.toInt()
        locationAddress = appPref.getLocationAddress()
        val extras = getIntent().getExtras()
        if (extras != null) {
            school_id = extras.getInt("id")
            schoolName = extras.getString("schoolname")
            schoolAddress = extras.getString("schooladress")
            formatId = extras.getInt("formatid")


            LoggerUtils.error("id", formId.toString())
            LoggerUtils.error("name", schoolName.toString())
            LoggerUtils.error("name", schoolAddress.toString())
            LoggerUtils.error("formatId", formatId.toString())
        }

        binding.tvSchoolName.text = "School Name - $schoolName"
        binding.tvSchoolAddress.text = "Address - $schoolAddress"
//        binding.surveyToolbar.imgGoBack.setOnClickListener {
//            listener?.onUserBackPressed()
//        }
        binding.txtSubmitAnswer.setOnClickListener {

            updateQuestionList.forEachIndexed { index, questionData ->
                LoggerUtils.error("TAG", questionData.userTextAnswer)
                val surveyInput = SurveyInput(
                    questionData.id,
                    school_id, teacherId?.toInt(),
                    questionData.question, questionData.formatName, questionData.type,
                    questionData.userTextAnswer, locationAddress,
                    district_id, district_name,
                    block_id, block_name, questionData.id
                )
                surveyRequest.add(surveyInput!!)
            }

                    LoggerUtils.error("districtid", district_id.toString())
                    LoggerUtils.error("name", "west")
                    LoggerUtils.error("formatid", formatId.toString())
                    viewModel.saveSurveyData(surveyRequest).observe(this) { response ->
                        when (response.status) {
                            Status.LOADING -> {
                                showProgress()
                            }
                            Status.SUCCESS -> {
                                hideProgressDialog()
                                showCustomToast(context, "Survey Saved Successfully")
                                finish()
                               // listener?.gotoHomeScreen()

                            }
                            Status.ERROR -> {
                                listener?.hideProgress()
                                response.message?.let {
                                    showAppAlert(this, "Alert", it, null)
                                }
                            }



                }
            }
        }






    }


    override fun onActPause() {

    }

    override fun onActResume() {
        viewModel.getAllQuestionByFormId(QuestListInput(formatId)).observe(
            this
        ) { response ->
            when (response.status) {
                Status.LOADING -> {
             showProgress()
                }
                Status.SUCCESS -> {
                    hideProgressDialog()
                    response.data?.body()?.data?.let {
                        updateQuestionList.addAll(it)
                        binding.rclyAllQuestion.apply {
                            layoutManager = LinearLayoutManager(
                                context,
                                RecyclerView.VERTICAL, false
                            )
                            questAdapter = QuestionListAdapter(updateQuestionList, object :
                                IQuestListInterface {
                                override fun refreshListAtPos(position: Int) {
                                    questAdapter.notifyItemChanged(position)

                                }

                                override fun refreshList() {
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        questAdapter.notifyDataSetChanged()
                                    }, 1000)
                                }

                                override fun updateRadioOptionAnswer(
                                    questData: QuestionData,
                                    value: String
                                ) {
                                    updateQuestionList.forEachIndexed { index, questionData ->
                                        if (questData.id == questionData.id) {
                                            questionData.userTextAnswer = value
                                        }
                                    }
                                }
                            })
                            adapter = questAdapter
                        }
                    }
                }
                Status.ERROR -> {
                    listener?.hideProgress()
                    response.message?.let {
                        showAppAlert(this, "Alert", it, null)
                    }
                }
            }
        }
binding.surveyToolbar.imgGoBack.setOnClickListener{
    finish()
}
    }


    override fun onActStop() {

    }


}