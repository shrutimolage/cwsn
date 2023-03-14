package com.cwsn.mobileapp.view.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.ActivityFieldBinding
import com.cwsn.mobileapp.model.home.Actlist_data
import com.cwsn.mobileapp.model.home.ClusterData
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.LoggerUtils
import com.cwsn.mobileapp.view.adapter.ClusterAdapterlist
import com.cwsn.mobileapp.view.base.BaseActivity
import com.cwsn.mobileapp.view.callback.*
import com.cwsn.mobileapp.viewmodel.home.HomeViewModel
import com.cwsn.mobileapp.viewmodel.monitoring.MonitorViewModel
import com.cwsn.mobileapp.viewmodel.task.TaskViewModel
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.collections.ArrayList


class FieldActivity : BaseActivity<ActivityFieldBinding>(), IMonitoringFragCallback {
    private var listener: IMonitoringFragCallback? = null
    private var block_id: Int? = null
    private var type_id: Int? = null
    private var cluster_id: Int? = null
    private val homeViewModel by inject<HomeViewModel>()

    private var clusterId: Int? = null
    private var clusterAdapterlist: ClusterAdapterlist? = null
    private var allClusters: List<ClusterData>? = null
    private var allActlist: ArrayList<Actlist_data>? = null

    private lateinit var filterlist: List<ClusterData>
    private val appPref by inject<AppPreferences>()
    private var clusterNames: MutableList<String> = mutableListOf()
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityFieldBinding {
        return ActivityFieldBinding.inflate(layoutInflater)
    }

    override fun getContext(): Context {
        return this@FieldActivity
    }

    override fun isToolBarEnable(): Boolean {
        return true
    }

    override fun getToolBar(): Toolbar? {
        return findViewById(R.id.toolbar)
    }

//    override fun getToolBarTitleView(): TextView? {
//        return findViewById(R.id.toolbar_title)
//    }

    override fun getToolBartTitle(): String {
        return "Visit"
    }

    override fun isBackArrowEnabled(): Boolean {
        return false
    }

    override fun getToolBarBackArrowView(): ImageView? {
        return findViewById(R.id.navigationBar)
    }

    override fun onUserBackPressed() {
        finish()
    }

    override fun showProgress() {
        showProgressDialog()
    }

    override fun hideProgress() {
hideProgressDialog()
    }

    override fun onToolbarBackArrowPress() {
        finish()
    }

    override fun isSetUpProgressDialog(): Boolean {
        return true
    }

    override fun onActStart() {
        try {
            if (context is IMonitoringFragCallback) {
                listener = this
            }
        } catch (ex: ClassCastException) {
            throw ClassCastException(
                context.toString()
                        + " must implement IMonitoringFragCallback"
            )
        }
    }

    fun getActContext(): Context? {
        return this@FieldActivity
    }

    override fun onActCreate() {
        val extras = getIntent().getExtras()
        if (extras != null) {
            type_id = extras.getInt("typeid", 0)
        }
        getActContext()
        val userLoginData = appPref.getUserLoginData()
        binding.tvBlockName.text = "Block Name:-" + userLoginData[appPref.KEY_BLOCK_NAME]
        block_id = userLoginData[appPref.KEY_BLOCK_ID]?.toInt()
        cluster_id = userLoginData[appPref.KEY_CLUSTER_ID]?.toInt()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.search, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        clusterAdapterlist = allClusters?.let {
            ClusterAdapterlist(this@FieldActivity, it, type_id, object : IClusterListItemClick {
                override fun IClusterListItemClick(
                    name: String?,
                    id: Int?
                ) {
                }

                override fun showDialog(id: Int?) {

                }
            })
        }
        allClusters = ArrayList()
        binding.rclyClusterlist.adapter = clusterAdapterlist
        val searchView: SearchView = searchItem.getActionView() as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                filter(msg)
                return false
            }
        })
        return true
    }

    private fun filter(text: String) {
        val filteredlist: ArrayList<ClusterData> = ArrayList()

        for (item in allClusters!!) {
            if (item.name?.toLowerCase()?.contains(text.toLowerCase(Locale.ROOT)) == true) {

                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {

            showAppAlert(this, "Alert", "No Data Found..", null)
        } else {
            binding.rclyClusterlist.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL,
                    false
                )
                adapter =
                    ClusterAdapterlist(
                        this@FieldActivity,
                        filteredlist!!, type_id,
                        object : IClusterListItemClick {
                            override fun IClusterListItemClick(name: String?, id: Int?) {
                            }

                            override fun showDialog(id: Int?) {
                                //showVisitingListDialog()
                            }
                        })
            }
        }

    }


//    }

    private fun getSelectedClusterId(clusterName: String): Int {
        var clusterId = 0
        allClusters?.forEachIndexed { _, clusterData ->
            if (clusterName == clusterData.name) {
                clusterData.id?.let {
                    clusterId = it
                }
            }
        }
        return clusterId

    }


    override fun onActPause() {

    }

    override fun onActResume() {
        fecthcluster()

    }

//    fun showVisitingListDialog() {
//
//        val visitingMode = VisitingMode()
//        visitingMode.show(supportFragmentManager, visitingMode.TAG)
//        val bundle = Bundle()
//
//        clusterId?.let { bundle.putInt("id", it) }
//        visitingMode.setArguments(bundle)
//
//
//    }

//    private fun fetchSchoolList(clusterId: Int) {
//        homeViewModel.getAllSchoolList(SchoolListInput(clusterId))
//            .observe(this) { response ->
//                when (response.status) {
//                    Status.LOADING -> {
//                        listener?.showProgress()
//                    }
//                    Status.SUCCESS -> {
//                        listener?.hideProgress()
//                        response.data?.body()?.data?.let { schoolList ->
//                            binding.rclySchoolList.visibility = View.VISIBLE
//                            binding.tvNoResult.visibility = View.GONE
//                            binding.rclySchoolList.apply {
//                                layoutManager = LinearLayoutManager(
//                                    context,
//                                    RecyclerView.VERTICAL,
//                                    false
//                                )
//
//                                adapter =
//                                    SchoolListAdapter(schoolList, object : ISchoolListItemClick {
//                                        override fun onSchoolListItemClick(
//                                            schoolId: Int?,
//                                            name: String?,
//                                            address: String?
//                                        ) {
//                                            //showTaskFormListDialog(schoolId, name, address)
//                                        }
//                                    })
//                            }
//                        }
//                    }
//                    Status.ERROR -> {
//                        listener?.hideProgress()
//                        response.message?.let {
//                            showAppAlert(getContext(), "Alert", it, null)
//                            binding.rclySchoolList.visibility = View.GONE
//                            binding.tvNoResult.visibility = View.VISIBLE
//                        }
//                    }
//                }
//            }
//    }
//
//    private fun showTaskFormListDialog(schoolId: Int?, name: String?, address: String?) {
//        taskViewModel.getAllTaskActList().observe(this) { response ->
//            when (response.status) {
//                Status.LOADING -> {
//                    listener?.showProgress()
//                }
//                Status.SUCCESS -> {
//                    listener?.hideProgress()
//                    response.data?.body()?.let { activityList ->
//                        val taskActvtyList = Gson().toJson(activityList)
//                        val taskListDialog = TaskFormListDialog.newInstance(taskActvtyList)
//                        taskListDialog.registerTaskDialogCallback(object : ITaskDialogCallback {
//                            override fun gotoQuestionsScreen(id: Int) {
//                                listener?.gotoSurveyQuestionScreen(id, name, address)
//                            }
//                        })
//                        taskListDialog.show(
//                            supportFragmentManager.beginTransaction(),
//                            TaskFormListDialog.TAG
//                        )
//                    }
//                }
//                Status.ERROR -> {
//                    listener?.hideProgress()
//                    response.message?.let {
//                        showAppAlert(this, "Alert", it, null)
//                    }
//                }
//            }
//        }
//
//
//    }
//

    fun fecthcluster() {
        homeViewModel.fetchAllCluster(block_id!!).observe(this) { response ->
            when (response.status) {
                Status.LOADING -> {
                    showProgress()
                }
                Status.SUCCESS -> {
                    hideProgressDialog()
                    clusterNames = mutableListOf()
                    response.data?.body()?.data?.let {
                        allClusters = it
                        allClusters!!.forEachIndexed { _, clusterData ->
                            binding.rclySchoolList.visibility = View.VISIBLE
//                            binding.tvNoResult.visibility = View.GONE
                            binding.rclyClusterlist.apply {
                                layoutManager = LinearLayoutManager(
                                    context,
                                    RecyclerView.VERTICAL,
                                    false
                                )

                                adapter =
                                    ClusterAdapterlist(
                                        this@FieldActivity,
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
                                                    clusterId = id
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
                        showAppAlert(this, "Alert", it, null)
                        // Toast.makeText(this,response.message.toString(),Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }


}