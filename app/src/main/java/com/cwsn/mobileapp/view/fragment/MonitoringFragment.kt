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
import com.cwsn.mobileapp.databinding.FragmentMonitoringBinding
import com.cwsn.mobileapp.model.home.ClusterData
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.view.adapter.SchoolListAdapter
import com.cwsn.mobileapp.view.base.BaseFragment
import com.cwsn.mobileapp.view.callback.IHomeFragCallback
import com.cwsn.mobileapp.view.callback.IMonitoringFragCallback
import com.cwsn.mobileapp.view.callback.ISchoolListItemClick
import com.cwsn.mobileapp.viewmodel.home.HomeViewModel
import com.cwsn.mobileapp.viewmodel.monitoring.MonitorViewModel
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import com.skydoves.powerspinner.PowerSpinnerView
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
class MonitoringFragment : BaseFragment<FragmentMonitoringBinding>(FragmentMonitoringBinding::inflate) {
    private var param1: String? = null
    private var param2: String? = null
    private var listener:IMonitoringFragCallback?=null
    private val monitorViewModel by inject<MonitorViewModel>()
    private val homeViewModel by inject<HomeViewModel>()
    private lateinit var allClusters: List<ClusterData>
    private var clusterNames:MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            if(context is IMonitoringFragCallback){
                listener= context
            }
        }
        catch (ex:ClassCastException){
            throw ClassCastException(context.toString()
                    + " must implement IMonitoringFragCallback")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.toolbarTitle.text="Monitoring"
        binding.toolbar.navigationBar.setOnClickListener {
            listener?.onUserBackPressed()
        }
        binding.spnrCluster.setOnSpinnerItemSelectedListener(object:OnSpinnerItemSelectedListener<String>{
            override fun onItemSelected(
                oldIndex: Int,
                oldItem: String?,
                newIndex: Int,
                newItem: String
            ) {
                val clusterId=getSelectedClusterId(newItem)
                fetchSchoolList()
            }

        })
    }

    private fun getSelectedClusterId(clusterName: String): Int {
        var clusterId =0
        allClusters.forEachIndexed { _, clusterData ->
            if(clusterName==clusterData.name){
                clusterData.id?.let {
                    clusterId=it
                }
            }
        }
        return clusterId
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.fetchAllCluster().observe(viewLifecycleOwner, { response->
            when(response.status){
                Status.LOADING->{
                    listener?.showProgress()
                }
                Status.SUCCESS->{
                    listener?.hideProgress()
                    response.data?.body()?.data?.let {
                        allClusters=it
                        allClusters.forEachIndexed { index, clusterData ->
                            clusterData.name?.let {name->
                                clusterNames.add(name)
                            }
                        }
                        clusterNames.add(0,"Select Cluster")
                        binding.spnrCluster.setItems(clusterNames)
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

    private fun fetchSchoolList() {
        monitorViewModel.getBlockWiseSchoolDetails(requireActivity(), R.raw.block_wise_school)
            .observe(viewLifecycleOwner, { response ->
                when (response.status) {
                    Status.LOADING -> {
                        listener?.showProgress()
                    }
                    Status.SUCCESS -> {
                        listener?.hideProgress()
                        response.data?.let { blockDetails ->
                            binding.tvBlockName.text = blockDetails.blockDetails?.name
                        }
                        response.data?.schoolList?.let { list ->
                            if (list.size > 0) {
                                binding.rclySchoolList.apply {
                                    layoutManager = LinearLayoutManager(
                                        requireActivity(),
                                        RecyclerView.VERTICAL,
                                        false
                                    )
                                    adapter =
                                        SchoolListAdapter(list, object : ISchoolListItemClick {
                                            override fun onStartSurvery(name: String?) {
                                                listener?.gotoTaskActivityScreen(name)
                                            }
                                        })
                                }
                            }
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
        val TAG: String="SummaryFragment"

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