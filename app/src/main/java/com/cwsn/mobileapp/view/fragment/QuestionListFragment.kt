package com.cwsn.mobileapp.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.FragmentQuestionListBinding
import com.cwsn.mobileapp.network.Status
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllQuestionData(requireActivity(),R.raw.questions)
            .observe(viewLifecycleOwner, { response->
                when(response.status){
                    Status.LOADING->{
                        listener?.showProgress()
                    }
                    Status.SUCCESS->{
                        listener?.hideProgress()
                        response.data?.questionist?.let {
                            binding.rclyAllQuestion.apply {
                                layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
                                adapter=QuestionListAdapter(it)

                            }
                        }
                    }
                    Status.ERROR->{
                        listener?.hideProgress()
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