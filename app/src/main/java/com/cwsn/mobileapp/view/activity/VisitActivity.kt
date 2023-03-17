package com.cwsn.mobileapp.view.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.ActivityVisitBinding
import com.cwsn.mobileapp.model.home.Actlist_data
import com.cwsn.mobileapp.model.school.SchoolListInput
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.LoggerUtils
import com.cwsn.mobileapp.view.adapter.ActvityLitstAdapterList
import com.cwsn.mobileapp.view.adapter.SchoolListAdapter
import com.cwsn.mobileapp.view.base.BaseActivity
import com.cwsn.mobileapp.view.callback.*
import com.cwsn.mobileapp.view.dialog.TaskFormListDialog
import com.cwsn.mobileapp.viewmodel.home.HomeViewModel
import com.cwsn.mobileapp.viewmodel.monitoring.MonitorViewModel
import com.google.gson.Gson
import org.koin.android.ext.android.inject

class VisitActivity : BaseActivity<ActivityVisitBinding>() , IAppBaseCallback {
    private var id: Int? = null
    private var Name: String? = null
    private var listener: IMonitoringFragCallback? = null
    private val homeViewModel by inject<HomeViewModel>()
    private var block_id: Int? = null
    private var cluster_id: Int? = null
    private var format_id: Int? = null
    private var type_id: Int? = null

    private val monitorViewModel by inject<MonitorViewModel>()
    private var allActlist: ArrayList<Actlist_data>? = null
    private val appPref by inject<AppPreferences>()
    private var ActvityName: MutableList<String> = mutableListOf()
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityVisitBinding {
        return ActivityVisitBinding.inflate(layoutInflater)
    }

    override fun getContext(): Context {
        return this@VisitActivity
    }

    override fun isToolBarEnable(): Boolean {
        return true
    }

    override fun getToolBar(): Toolbar? {
        return findViewById(R.id.toolbar)


    }
    override fun getToolBartTitle(): String {
        return "Profile"
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

    }

    override fun onActCreate() {
        val userLoginData = appPref.getUserLoginData()
        block_id = userLoginData[appPref.KEY_BLOCK_ID]?.toInt()
        cluster_id = userLoginData[appPref.KEY_CLUSTER_ID]?.toInt()
        val extras = getIntent().getExtras()
        if (extras != null) {
            type_id = extras.getInt("typeid")
        }


    }

    override fun onActPause() {

    }

    override fun onActResume() {
        binding.toolbar.toolbarTitle.text="Activities"

        formactivties()
        binding.imgCloseDialog.setOnClickListener {
            //   dismiss()
        }

        binding.tvSchool.setOnClickListener {
            val intent = Intent(this, SchoolTypeActivity::class.java)
            intent.putExtra("name", Name)
            intent.putExtra("id", id)
            startActivity(intent)
            startActivity(intent)
        }

        binding.tvHome.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

        binding.tvResorces.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }


    }

    private fun showToastMessage(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.show()
    }

    fun formactivties() {
        monitorViewModel.activitiesList(type_id).observe(this) { response ->
            when (response.status) {
                Status.LOADING -> {
                    showProgress()
                }
                Status.SUCCESS -> {
                   hideProgress()
                    response.data?.body()?.data?.let {
                        allActlist = it
                        allActlist!!.forEachIndexed { _, list ->
                            binding.rclyActvyList.apply {
                                layoutManager = GridLayoutManager(context, 2)


                                adapter =
                                    ActvityLitstAdapterList(VisitActivity(), allActlist!!, object :
                                        IActivityTypeCallback {
                                        override fun getTaskId(id: Int?) {
                                            LoggerUtils.error("format_id", id.toString())
                                            format_id = id
                                            cluster_id?.let { fetchSchoolList(it) }
//                                            val taskActvtyList = Gson().toJson(allActlist)
//                                            val taskListDialog = TaskFormListDialog.newInstance(taskActvtyList)
//                                            taskListDialog.registerTaskDialogCallback(object:
//                                                ITaskDialogCallback {
//                                                override fun gotoQuestionsScreen(id: Int) {
//                                                 //   listener?.gotoSurveyQuestionScreen(id,name,address)
//                                                }
//                                            })
//                                            taskListDialog.show(supportFragmentManager,
//                                                TaskFormListDialog.TAG)

                                    }




                                    }
                                    )


                            }
                        }
                    }

                }
                Status.ERROR -> {
                  hideProgress()
                    response.message?.let {
                        showAppAlert(this, "Alert", it, null)
                        // Toast.makeText(this,response.message.toString(),Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }

    fun fetchSchoolList(clusterId: Int) {
        homeViewModel.getAllSchoolList(SchoolListInput(clusterId))
            .observe(this) { response ->
                when (response.status) {
                    Status.LOADING -> {
                        showProgress()
                    }
                    Status.SUCCESS -> {
                      hideProgress()
                        response.data?.body()?.data?.let { schoolList ->
                            binding.rclySchoolList.visibility = View.VISIBLE
                            binding.tvNoResult.visibility = View.GONE
                            binding.rclySchoolList.apply {
                                layoutManager = LinearLayoutManager(
                                    context,
                                    RecyclerView.VERTICAL,
                                    false
                                )

                                adapter =
                                    format_id?.let {
                                        SchoolListAdapter(
                                            schoolList,
                                            object : ISchoolListItemClick {
                                                override fun onSchoolListItemClick(
                                                    schoolId: Int?,
                                                    name: String?,
                                                    address: String?
                                                ) {
                                                    //showTaskFormListDialog(schoolId, name, address)
                                                }
                                            },
                                            it
                                        )
                                    }
                            }
                        }
                    }
                    Status.ERROR -> {
                   hideProgress()
                        response.message?.let {
                            showAppAlert(getContext(), "Alert", it, null)
                            binding.rclySchoolList.visibility = View.GONE
                            binding.tvNoResult.visibility = View.VISIBLE
                        }
                    }
                }
            }
    }

}


