package com.cwsn.mobileapp.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.adapter.home.ItemCountAdapter
import com.cwsn.mobileapp.adapter.home.SchoolListAdapter
import com.cwsn.mobileapp.databinding.FragmentHomeBinding
import com.cwsn.mobileapp.model.home.ClusterData
import com.cwsn.mobileapp.model.home.ItemCount
import com.cwsn.mobileapp.model.school.SchoolListInput
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.toast
import com.cwsn.mobileapp.view.base.BaseFragment
import com.cwsn.mobileapp.view.callback.HomeFragCallback
import com.cwsn.mobileapp.view.callback.ISchoolListCallback
import com.cwsn.mobileapp.viewmodel.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("MoveLambdaOutsideParentheses")
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private lateinit var allClusters: List<ClusterData>
    private var param1: String? = null
    private var param2: String? = null
    private val homeViewModel by viewModel<HomeViewModel>()
    private var clusterNames:MutableList<String> = mutableListOf()
    private var listener: HomeFragCallback?=null
    private var itemCountList:MutableList<ItemCount> = mutableListOf()

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
            if(context is HomeFragCallback){
                listener= context
            }
        }
        catch (ex:ClassCastException){
            throw ClassCastException(context.toString()
                    + " must implement HomeFragCallback")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*getAllSchoolCount()
        getClusterList()*/

    }

    private fun getAllSchoolList(clusterId: Int) {
        val input=SchoolListInput(clusterId)
        homeViewModel.getAllSchoolList(input).observe(this, { response->
            when(response.status){
                Status.LOADING->{
                    listener?.showProgress()
                }
                Status.SUCCESS->{
                    listener?.hideProgress()
                    binding.rclySchoolList.visibility=View.VISIBLE
                    binding.tvNoResult.visibility=View.GONE
                    response.data?.body()?.data?.let {
                        binding.rclySchoolList.apply {
                            val schoolListAdapter=SchoolListAdapter(it,object:ISchoolListCallback{
                                override fun startSchoolSurvey(
                                    schoolId: Int?,
                                    name: String?,
                                    address: String?
                                ) {
                                    listener?.gotoSchoolSurvey(schoolId,name,address)
                                }
                            })
                            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
                            adapter=schoolListAdapter
                        }
                    }
                }
                Status.ERROR->{
                   listener?.hideProgress()
                    response.message?.let{
                        toast(it,requireActivity())
                        binding.rclySchoolList.visibility=View.GONE
                        binding.tvNoResult.visibility=View.VISIBLE
                    }
                }
            }
        })
    }



    private fun getSelectedClusterId(clusterName: String): Int {
        var result =0
        for(item in allClusters){
            if(item.name==clusterName){
                result=item.id!!
            }
        }
        return result
    }

    private fun getClusterList() {
        homeViewModel.fetchAllCluster().observe(viewLifecycleOwner, { response->
            when(response.status){
                Status.SUCCESS->{
                    listener?.hideProgress()
                    response.data?.body()?.data?.let { clusters->
                        allClusters=clusters
                        //toast("cluster size ${clusters.size}",requireActivity())
                        clusterNames= mutableListOf()
                        for(cluster in clusters){
                            cluster.name?.let { clusterNames.add(it) }
                        }
                        clusterNames.add(0,"Select Cluster")
                        binding.spnrAllCluster.hint = "Select Cluster"
                        binding.spnrAllCluster.setItems(clusterNames)
                    }
                }
                Status.ERROR->{
                    listener?.hideProgress()
                    response.message?.let{
                        toast(it,requireActivity())
                    }
                }
                Status.LOADING->{
                    listener?.showProgress()
                }
            }
        })
    }

    private fun getAllSchoolCount() {
        homeViewModel.getAllDashboardCount().observe(viewLifecycleOwner, { response->
            when(response.status){
                Status.LOADING->{
                    listener?.showProgress()
                }
                Status.SUCCESS->{
                    listener?.hideProgress()
                    itemCountList = mutableListOf()
                    response.data?.body()?.data?.let { countData->
                        itemCountList.add(ItemCount("Total School",countData.totalSchool.toString(),R.drawable.ic_total_school))
                        itemCountList.add(ItemCount("Visited School",countData.totalVerifiedSchool.toString(),R.drawable.ic_total_school_visted))
                        itemCountList.add(ItemCount("Pending School",countData.totalPendingSchool.toString(),R.drawable.ic_pending_school))
                       showSchoolCountDetails(itemCountList)
                    }
                }
                Status.ERROR->{
                    listener?.hideProgress()
                    response.message?.let {
                        toast(it,requireActivity())
                    }
                }
            }
        })
    }

    private fun showSchoolCountDetails(itemCountList: MutableList<ItemCount>) {
        binding.rclyDashboardItem.apply {
            layoutManager=GridLayoutManager(requireActivity(),2)
            adapter=ItemCountAdapter(itemCountList)
        }
    }

    companion object {
        val TAG: String="HomeFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}