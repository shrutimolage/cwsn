package com.cwsn.mobileapp.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.adapter.home.MonitorActvityTypeAdapter
import com.cwsn.mobileapp.databinding.FragmentMonitoringBinding
import com.cwsn.mobileapp.model.home.Actlist_data
import com.cwsn.mobileapp.model.home.ClusterData
import com.cwsn.mobileapp.model.login.Data
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.LoggerUtils
import com.cwsn.mobileapp.view.adapter.ClusterAdapterlist
import com.cwsn.mobileapp.view.base.BaseFragment
import com.cwsn.mobileapp.view.callback.IActivityTypeItemClick
import com.cwsn.mobileapp.view.callback.IClusterListItemClick
import com.cwsn.mobileapp.view.callback.IMonitoringFragCallback
import com.cwsn.mobileapp.viewmodel.home.HomeViewModel
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
    private var block_id: Int? = null
    private var type_id: Int? = null
    private var selected_clusterId: Int? = null
    private var allClusters: List<ClusterData>? = null
    private var allActlist: ArrayList<Actlist_data>? = null
    private var cluster_id: Int? = null
    private var listener: IMonitoringFragCallback? = null
    private val homeViewModel by inject<HomeViewModel>()
    private lateinit var allact: List<Data>

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
        val userLoginData = appPref.getUserLoginData()

        block_id = userLoginData[appPref.KEY_BLOCK_ID]?.toInt()
        cluster_id = userLoginData[appPref.KEY_CLUSTER_ID]?.toInt()
        binding.listBlockname.text="List of Cluster in : "+ userLoginData[appPref.KEY_BLOCK_NAME]  +  "  "  +"Block"

        binding.tvBlockName.text = "Block Name:-" + userLoginData[appPref.KEY_BLOCK_NAME]
        binding.monitor.toolbarTitle.text = "Monitoring"
        binding.monitor.navigationBar.setOnClickListener {
            listener?.onUserBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        fecthActivityType()

    }
    fun fecthActivityType() {
        homeViewModel.getActivityType().observe(this) { response ->
            when (response.status) {
                Status.LOADING -> {
                    listener?.showProgress()

                }
                Status.SUCCESS -> {
                    listener?.hideProgress()
                    response.data?.body()?.data?.let {
                        binding.actList.apply {
                            layoutManager = LinearLayoutManager(
                                requireActivity(),
                                RecyclerView.HORIZONTAL,
                                false
                            )
                            adapter =
                                MonitorActvityTypeAdapter(this@MonitoringFragment,it,object:IActivityTypeItemClick{
                                    override fun getActvityTypeId(typeid: Int) {
                                        type_id=typeid
                                        LoggerUtils.error("typeid",type_id.toString())
                                    }


                                })


                        }

                    }
                }


                Status.ERROR -> {
                    listener?.hideProgress()
                    response.message?.let {
                        showAppAlert(requireActivity(), "Alert", it, null)
                        // Toast.makeText(this,response.message.toString(),Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }


fun fecthcluster() {
    homeViewModel.fetchAllCluster(block_id!!).observe(this) { response ->
        when (response.status) {
            Status.LOADING -> {
                listener?.showProgress()
            }
            Status.SUCCESS -> {

                listener?.hideProgress()
                binding.listBlockname.visibility=View.VISIBLE
                binding.view.visibility=View.VISIBLE
                clusterNames = mutableListOf()
                response.data?.body()?.data?.let {
                    allClusters = it
                    allClusters!!.forEachIndexed { _, clusterData ->
                        binding.rclyClusterlist.apply {
                            layoutManager = LinearLayoutManager(
                                context,
                                RecyclerView.VERTICAL,
                                false
                            )

                            adapter =
                                ClusterAdapterlist(
                                    requireActivity(),
                                    allClusters!!,
                                    type_id,
                                    object : IClusterListItemClick {
                                        override fun IClusterListItemClick(
                                            name: String?,
                                            id: Int?
                                        ) {
                                            if (id != null) {
                                                LoggerUtils.error("name", name)
                                                LoggerUtils.error("name", id.toString())
                                                selected_clusterId = id
                                            }


                                        }

                                        override fun showDialog(id: Int?) {

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
                    // Toast.makeText(this,response.message.toString(),Toast.LENGTH_LONG).show()
                }
            }
        }

    }

}
    fun inoffice() {
        showCustomToast(requireActivity(), "in-office")
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