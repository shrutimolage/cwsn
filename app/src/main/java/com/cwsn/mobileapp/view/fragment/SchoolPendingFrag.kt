package com.cwsn.mobileapp.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.FragmentSchoolPendingBinding
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.view.adapter.SchoolPendingAdapter
import com.cwsn.mobileapp.view.base.BaseFragment
import com.cwsn.mobileapp.view.callback.ISchoolListItemClick
import com.cwsn.mobileapp.view.callback.ISchoolPendingFragCallback
import com.cwsn.mobileapp.view.callback.ITaskDialogCallback
import com.cwsn.mobileapp.view.dialog.TaskFormListDialog
import com.cwsn.mobileapp.viewmodel.home.HomeViewModel
import com.cwsn.mobileapp.viewmodel.task.TaskViewModel
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import java.lang.Exception

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SchoolPendingFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class SchoolPendingFrag : BaseFragment<FragmentSchoolPendingBinding>(FragmentSchoolPendingBinding::inflate) {
    private var param1: String? = null
    private var param2: String? = null
    private var listener:ISchoolPendingFragCallback?=null
    private val homeViewModel by inject<HomeViewModel>()
    private val taskViewModel by inject<TaskViewModel>()

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
            if(context is ISchoolPendingFragCallback){
                listener=context
            }
        }
        catch (ex: Exception){
            ex.printStackTrace()
            throw ClassCastException(context.toString()
                    + " must implement ISchoolPendingFragCallback")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.toolbarTitle.text="School Pending"
        binding.toolbar.navigationBar.setOnClickListener {
            listener?.onUserBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getAllPendingSchool().observe(viewLifecycleOwner,Observer{response->
            when(response.status){
                Status.LOADING->{
                    listener?.showProgress()
                }
                Status.SUCCESS->{
                    listener?.hideProgress()
                    response.data?.body()?.data?.let {
                        binding.rclySchoolpending.apply {
                            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
                            adapter=SchoolPendingAdapter(it,object: ISchoolListItemClick{
                                override fun onSchoolListItemClick(
                                    schoolId: Int?,
                                    name: String?,
                                    address: String?
                                ) {
                                    showTaskFormListDialog(schoolId,name,address)
                                    //listener?.gotoQuestionListScreen()
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

    private fun showTaskFormListDialog(schoolId: Int?, name: String?, address: String?) {
        taskViewModel.getAllTaskActList().observe(viewLifecycleOwner, Observer { response->
            when (response.status) {
                Status.LOADING -> {
                    listener?.showProgress()
                }
                Status.SUCCESS -> {
                    listener?.hideProgress()
                    response.data?.body()?.let {activityList ->
                        val taskActvtyList = Gson().toJson(activityList)
                        val taskListDialog = TaskFormListDialog.newInstance(taskActvtyList)
                        taskListDialog.registerTaskDialogCallback(object:ITaskDialogCallback{
                            override fun gotoQuestionsScreen(id: Int) {
                                listener?.gotoSurveyQuestionScreen(id,name,address)
                            }
                        })
                        taskListDialog.show(requireActivity().supportFragmentManager,TaskFormListDialog.TAG)
                    }
                }
                Status.ERROR -> {
                    listener?.hideProgress()
                    response.message?.let {
                        showAppAlert(requireActivity(), "Alert", it, null)
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
         * @return A new instance of fragment SchoolPendingFrag.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SchoolPendingFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}