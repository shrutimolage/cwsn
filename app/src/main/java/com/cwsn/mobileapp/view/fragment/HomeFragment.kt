package com.cwsn.mobileapp.view.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.cwsn.mobileapp.adapter.home.DashboardItemAdapter
import com.cwsn.mobileapp.adapter.home.ItemCountAdapter
import com.cwsn.mobileapp.databinding.FragmentHomeBinding
import com.cwsn.mobileapp.model.home.ClusterData
import com.cwsn.mobileapp.model.home.ItemCount
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.Utils
import com.cwsn.mobileapp.view.activity.UserProfileActivity
import com.cwsn.mobileapp.view.base.BaseFragment
import com.cwsn.mobileapp.view.callback.IDashboardListCallback
import com.cwsn.mobileapp.view.callback.IHomeFragCallback
import com.cwsn.mobileapp.viewmodel.home.HomeViewModel
import org.koin.android.ext.android.inject
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
    private val appPref by inject<AppPreferences>()
    private var clusterNames:MutableList<String> = mutableListOf()
    private var listener: IHomeFragCallback?=null
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
            if(context is IHomeFragCallback){
                listener= context
            }
        }
        catch (ex:ClassCastException){
            throw ClassCastException(context.toString()
                    + " must implement HomeFragCallback")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.toolbarTitle.text = "Dashboard"
        binding.toolbar.navigationBar.setOnClickListener {
            listener?.toggleAppTopBar()
        }
        val dashboardItem = Utils.generateDashboardItem()
        binding.rclyDashboardItem.apply {
            layoutManager=GridLayoutManager(requireActivity(),2)
            adapter=DashboardItemAdapter(dashboardItem,object:IDashboardListCallback{
                override fun onItemClicked(itemName: String) {
                    listener?.onNavigateOptionScreen(itemName)
                }
            })
        }
        binding.toolbar.cimgProfileIcon.setOnClickListener {
            startActivity(Intent(context,UserProfileActivity::class.java))
        }
        binding.toolbar.imgLogoutApp.setOnClickListener {
            appPref.performAppLogout()
            listener?.gotoLoginScreen()
        }
        binding.tvTeacherName.text="Welcome,${appPref.getUserLoginData()[appPref.KEY_TEACHER_NAME]}"

    }


    /*private fun getClusterList() {
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
    }*/

    /*private fun getAllSchoolCount() {
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
    }*/

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