package com.cwsn.mobileapp.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.FragmentMonitoringBinding
import com.cwsn.mobileapp.model.home.ClusterData
import com.cwsn.mobileapp.model.school.SchoolListInput
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.LoggerUtils
import com.cwsn.mobileapp.view.adapter.SchoolListAdapter
import com.cwsn.mobileapp.view.base.BaseFragment
import com.cwsn.mobileapp.view.callback.IMonitoringFragCallback
import com.cwsn.mobileapp.view.callback.ISchoolListItemClick
import com.cwsn.mobileapp.view.callback.ITaskDialogCallback
import com.cwsn.mobileapp.view.dialog.TaskFormListDialog
import com.cwsn.mobileapp.viewmodel.home.HomeViewModel
import com.cwsn.mobileapp.viewmodel.monitoring.MonitorViewModel
import com.cwsn.mobileapp.viewmodel.task.TaskViewModel
import com.google.gson.Gson
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import org.koin.android.ext.android.inject

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MonitoringFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("MoveLambdaOutsideParentheses")
class MonitoringFragment :
    BaseFragment<FragmentMonitoringBinding>(FragmentMonitoringBinding::inflate) {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: IMonitoringFragCallback? = null
    private val monitorViewModel by inject<MonitorViewModel>()
    private val homeViewModel by inject<HomeViewModel>()
    private val taskViewModel by inject<TaskViewModel>()
    private lateinit var allClusters: List<ClusterData>
    private val appPref by inject<AppPreferences>()
    private var clusterNames: MutableList<String> = mutableListOf()

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
            if (context is IMonitoringFragCallback) {
                listener = context
            }
        } catch (ex: ClassCastException) {
            throw ClassCastException(
                context.toString()
                        + " must implement IMonitoringFragCallback"
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.toolbarTitle.text = "Monitoring"
        binding.toolbar.navigationBar.setOnClickListener {
            listener?.onUserBackPressed()
        }
        binding.spnrCluster.setOnSpinnerItemSelectedListener(object :
            OnSpinnerItemSelectedListener<String> {
            override fun onItemSelected(
                oldIndex: Int,
                oldItem: String?,
                newIndex: Int,
                newItem: String
            ) {
                val clusterId = getSelectedClusterId(newItem)
                LoggerUtils.error("CLUSTER ID", "$clusterId")
                fetchSchoolList(clusterId)
            }
        })
        val userLoginData = appPref.getUserLoginData()
        binding.tvBlockName.text = "Block Name:-" + userLoginData[appPref.KEY_BLOCK_NAME]
    }

    private fun getSelectedClusterId(clusterName: String): Int {
        var clusterId = 0
        allClusters.forEachIndexed { _, clusterData ->
            if (clusterName == clusterData.name) {
                clusterData.id?.let {
                    clusterId = it
                }
            }
        }
        return clusterId
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.fetchAllCluster().observe(viewLifecycleOwner, { response ->
            when (response.status) {
                Status.LOADING -> {
                    listener?.showProgress()
                }
                Status.SUCCESS -> {
                    listener?.hideProgress()
                    clusterNames = mutableListOf()
                    response.data?.body()?.data?.let {
                        allClusters = it
                        allClusters.forEachIndexed { _, clusterData ->
                            clusterData.name?.let { name ->
                                clusterNames.add(name)
                            }
                        }
                        clusterNames.add(0, "Select Cluster")
                        binding.spnrCluster.setItems(clusterNames)
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

    private fun fetchSchoolList(clusterId: Int) {
        homeViewModel.getAllSchoolList(SchoolListInput(clusterId))
            .observe(viewLifecycleOwner, { response ->
                when (response.status) {
                    Status.LOADING -> {
                        listener?.showProgress()
                    }
                    Status.SUCCESS -> {
                        listener?.hideProgress()
                        response.data?.body()?.data?.let { schoolList ->
                            binding.rclySchoolList.visibility = View.VISIBLE
                            binding.tvNoResult.visibility = View.GONE
                            binding.rclySchoolList.apply {
                                layoutManager = LinearLayoutManager(
                                    requireActivity(),
                                    RecyclerView.VERTICAL,
                                    false
                                )
                                adapter =
                                    SchoolListAdapter(schoolList, object : ISchoolListItemClick {
                                        override fun onSchoolListItemClick(
                                            schoolId: Int?,
                                            name: String?,
                                            address: String?)
                                        {
                                            showTaskFormListDialog(schoolId,name,address)
                                        }
                                    })
                            }
                        }
                    }
                    Status.ERROR -> {
                        listener?.hideProgress()
                        response.message?.let {
                            showAppAlert(requireActivity(), "Alert", it, null)
                            binding.rclySchoolList.visibility = View.GONE
                            binding.tvNoResult.visibility = View.VISIBLE
                        }
                    }
                }
            })
    }

    private fun showTaskFormListDialog(schoolId: Int?, name: String?, address: String?) {
        taskViewModel.getAllTaskActList().observe(viewLifecycleOwner, { response ->
            when (response.status) {
                Status.LOADING -> {
                    listener?.showProgress()
                }
                Status.SUCCESS -> {
                    listener?.hideProgress()
                    response.data?.body()?.let { activityList ->
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
        val TAG: String = "SummaryFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SummaryFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MonitoringFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}