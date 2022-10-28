package com.cwsn.mobileapp.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.FragmentQuestionListBinding
import com.cwsn.mobileapp.model.questions.QuestListInput
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.Utils
import com.cwsn.mobileapp.view.adapter.QuestionListAdapter
import com.cwsn.mobileapp.view.base.BaseFragment
import com.cwsn.mobileapp.view.callback.IQuestionListCallback
import com.cwsn.mobileapp.view.callback.IResourceRoomCallback
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
    private var schoolAddress: String?=null
    private var schoolName: String?=null
    private var formId: Int?=0
    private var param1: String? = null
    private var param2: String? = null
    private val viewModel by inject<SurveyViewModel>()
    private var listener: IQuestionListCallback?=null

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
        formId = arguments?.getInt(Utils.FORMID,0)
        schoolName = arguments?.getString(Utils.SCHOOLNAME)
        schoolAddress = arguments?.getString(Utils.SCHOOL_ADDRS)
        binding.tvSchoolName.text="School Name - $schoolName"
        binding.tvSchoolAddress.text="Address - $schoolAddress"
        binding.surveyToolbar.imgGoBack.setOnClickListener {
            listener?.onUserBackPressed()
        }
        binding.txtSubmitAnswer.setOnClickListener {
            listener?.showProgress()
            Handler(Looper.getMainLooper()).postDelayed({
                listener?.hideProgress()
                listener?.gotoHomeScreen()
            },1000)
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
                            binding.rclyAllQuestion.apply {
                                layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
                                adapter=QuestionListAdapter(it)
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