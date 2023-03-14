package com.cwsn.mobileapp.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.FragmentQuestionListBinding
import com.cwsn.mobileapp.model.questions.QuestListInput
import com.cwsn.mobileapp.model.questions.QuestionData
import com.cwsn.mobileapp.model.survey.SurveyInput
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.LoggerUtils
import com.cwsn.mobileapp.utils.Utils
import com.cwsn.mobileapp.view.adapter.QuestionListAdapter
import com.cwsn.mobileapp.view.base.BaseFragment
import com.cwsn.mobileapp.view.callback.IQuestListInterface
import com.cwsn.mobileapp.view.callback.IQuestionListCallback
import com.cwsn.mobileapp.viewmodel.survey.SurveyViewModel
import org.koin.android.ext.android.inject
import java.lang.Exception

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuestionListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("MoveLambdaOutsideParentheses")
class QuestionListFragment : BaseFragment<FragmentQuestionListBinding>(FragmentQuestionListBinding::inflate)
{
    private var locationAddress: String?=null
    private var teacherId: String?=null
    private lateinit var questAdapter: QuestionListAdapter
    private var schoolAddress: String?=null
    private var schoolName: String?=null
    private var formId: Int?=0
    private var param1: String? = null
    private var param2: String? = null
    private val viewModel by inject<SurveyViewModel>()
    private var listener: IQuestionListCallback?=null
    private var updateQuestionList:MutableList<QuestionData> = mutableListOf()
    private var surveyRequest:MutableList<SurveyInput> = mutableListOf()
    private val appPref by inject<AppPreferences>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val callback:OnBackPressedCallback=object:OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                listener?.onUserBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            if(context is IQuestionListCallback){
                listener=context
            }
        }
        catch (ex: Exception){
            ex.printStackTrace()
            throw ClassCastException(context.toString()
                    + " must implement IQuestionListCallback")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teacherId = appPref.getUserLoginData()[appPref.KEY_TEACHER_ID]
        locationAddress = appPref.getLocationAddress()
        formId = arguments?.getInt(Utils.FORMID, 0)
        schoolName = arguments?.getString(Utils.SCHOOLNAME)
        schoolAddress = arguments?.getString(Utils.SCHOOL_ADDRS)
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
                    questionData.schoolId?.toInt(), teacherId?.toInt(),
                    questionData.question, questionData.type, questionData.formatName,
                    questionData.userTextAnswer, locationAddress, formId
                )
                surveyRequest.add(surveyInput)
            }

//            viewModel.saveSurveyData(surveyRequest).observe(viewLifecycleOwner, { response->
//                when(response.status){
//                    Status.LOADING->{
//                        listener?.showProgress()
//                    }
//                    Status.SUCCESS->{
//                        listener?.hideProgress()
//                        showCustomToast(requireActivity(),"Survey Saved Successfully")
//                        listener?.gotoHomeScreen()
//                    }
//                    Status.ERROR->{
//                        listener?.hideProgress()
//                        response.message?.let {
//                            showAppAlert(requireActivity(),"Alert",it,null)
//                        }
//                    }
//                }
//            })
//        }
        }
    }
    override fun onResume() {
        super.onResume()
        viewModel.getAllQuestionByFormId(QuestListInput(formId)).observe(viewLifecycleOwner,
            {response->
                when(response.status){
                    Status.LOADING->{
                        listener?.showProgress()
                    }
                    Status.SUCCESS->{
                        listener?.hideProgress()
                        response.data?.body()?.data?.let {
                            updateQuestionList.addAll(it)
                            binding.rclyAllQuestion.apply {
                                layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
                                questAdapter=QuestionListAdapter(updateQuestionList,object:IQuestListInterface{
                                    override fun refreshListAtPos(position: Int) {
                                        questAdapter.notifyItemChanged(position)
                                    }

                                    override fun refreshList() {
                                        Handler(Looper.getMainLooper()).postDelayed({
                                            questAdapter.notifyDataSetChanged()
                                        },1000)
                                    }

                                    override fun updateRadioOptionAnswer(
                                        questData: QuestionData,
                                        value: String
                                    ) {
                                        updateQuestionList.forEachIndexed { index, questionData ->
                                            if(questData.id==questionData.id){
                                                questionData.userTextAnswer=value
                                            }
                                        }
                                    }
                                })
                                adapter=questAdapter
                            }
                        }
                    }
                    Status.ERROR->{
                        listener?.hideProgress()
                        response.message?.let {
                            showAppAlert(requireActivity(),"Alert",it,null)
                        }
                    }
                }
            })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuestionListFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuestionListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}