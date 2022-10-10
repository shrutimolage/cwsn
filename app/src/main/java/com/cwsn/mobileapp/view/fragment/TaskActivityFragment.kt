package com.cwsn.mobileapp.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.FragmentTaskActivityBinding
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.view.adapter.TaskActivityAdapter
import com.cwsn.mobileapp.view.base.BaseFragment
import com.cwsn.mobileapp.view.callback.IMonitoringFragCallback
import com.cwsn.mobileapp.view.callback.ITaskActivityCallback
import com.cwsn.mobileapp.view.callback.ITaskActvtFragCallback
import com.cwsn.mobileapp.viewmodel.task.TaskViewModel
import org.koin.android.ext.android.inject

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TaskActivityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("MoveLambdaOutsideParentheses")
class TaskActivityFragment : BaseFragment<FragmentTaskActivityBinding>(FragmentTaskActivityBinding::inflate) {
    private var param1: String? = null
    private var param2: String? = null
    private var listener:ITaskActvtFragCallback?=null
    private val viewModel by inject<TaskViewModel>()

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
        try{
            if(context is ITaskActvtFragCallback){
                listener= context
            }
        }
        catch (ex:ClassCastException){
            throw ClassCastException(context.toString()
                    + " must implement ITaskActvtFragCallback")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.navigationBar.setOnClickListener {
            listener?.onUserBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllTaskActivityList(requireActivity(),R.raw.task_activity)
            .observe(viewLifecycleOwner, { response->
                when(response.status){
                    Status.LOADING->{
                        listener?.showProgress()
                    }
                    Status.SUCCESS->{
                        listener?.hideProgress()
                        response.data?.tasklist?.let {
                            binding.rclyTaskActvyList.apply {
                                layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
                                adapter=TaskActivityAdapter(it,object:ITaskActivityCallback{
                                    override fun onTaskItemClicked() {
                                        listener?.gotoSurveyQuestionScreen()
                                    }
                                })
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
         * @return A new instance of fragment TaskActivityFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TaskActivityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}