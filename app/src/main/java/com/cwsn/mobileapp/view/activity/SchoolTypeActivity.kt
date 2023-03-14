package com.cwsn.mobileapp.view.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.ActivitySchoolTypeBinding
import com.cwsn.mobileapp.model.home.ClusterData
import com.cwsn.mobileapp.model.school.SchoolListInput
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.LoggerUtils
import com.cwsn.mobileapp.view.adapter.SchoolListAdapter
import com.cwsn.mobileapp.view.base.BaseActivity
import com.cwsn.mobileapp.view.callback.IMonitoringFragCallback
import com.cwsn.mobileapp.view.callback.ISchoolListItemClick
import com.cwsn.mobileapp.viewmodel.home.HomeViewModel
import com.cwsn.mobileapp.viewmodel.monitoring.MonitorViewModel
import com.cwsn.mobileapp.viewmodel.task.TaskViewModel
import org.koin.android.ext.android.inject

class SchoolTypeActivity :BaseActivity<ActivitySchoolTypeBinding>() {
    private var radioButton: RadioButton? = null
    private  val clusterdId:Int?=null
    private var id:Int?=null
    private var Name:String?=null
    private var listener: IMonitoringFragCallback? = null
    private val monitorViewModel by inject<MonitorViewModel>()
    private val homeViewModel by inject<HomeViewModel>()
    private val taskViewModel by inject<TaskViewModel>()
    private lateinit var allClusters: List<ClusterData>
    private val appPref by inject<AppPreferences>()
    private var clusterNames: MutableList<String> = mutableListOf()
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySchoolTypeBinding {

        return ActivitySchoolTypeBinding.inflate(layoutInflater)
    }

    override fun getContext(): Context {
        return this@SchoolTypeActivity
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
        return "School"
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

    override fun onToolbarBackArrowPress() {
        finish()
    }

    override fun isSetUpProgressDialog(): Boolean {
        return true
    }

    override fun onActStart() {

    }

    override fun onActCreate() {
        val extras = getIntent().getExtras()
        if (extras != null) {

            id = extras.getInt("id")
//            Name = extras?.getString("name")
//            LoggerUtils.error("id", id.toString())
//            LoggerUtils.error("name", Name)
        }

    }

    override fun onActPause() {

    }

    override fun onActResume() {

        binding.radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
//                Toast.makeText(
//                    applicationContext, " On checked change :" +
//                            " ${radio.text}",
//                    Toast.LENGTH_SHORT
//                ).show()
                LoggerUtils.debug(
                    "checkid",
                    "checkedId :: $checkedId"
                )

                if (checkedId == R.id.school) {
//                    Toast.makeText(
//                        applicationContext, " On checked change :" +
//                                " ${checkedId.toString()}",
//                        Toast.LENGTH_SHORT
//                    ).show()
                 //   id?.let { fetchSchoolList(it) }
                }
                // RadioButton rb_present = group.findViewById(R.id.rb_present);
                // RadioButton rb_present = group.findViewById(R.id.rb_present);
                if (checkedId == R.id.kgbv) {
//                    Toast.makeText(
//                        applicationContext, " On checked change :" +
//                                " ${checkedId.toString()}",
//                        Toast.LENGTH_SHORT
//                    ).show()
                  //  id?.let { fetchSchoolList(it) }
                }
            })
        // Get radio group selected status and text using button click event
//      binding.button.setOnClickListener{
//            // Get the checked radio button id from radio group
//            var id: Int = binding.radioGroup.checkedRadioButtonId
//            if (id!=-1){ // If any radio button checked from radio group
//                // Get the instance of radio button using id
//                val radio:RadioButton = findViewById(id)
//                Toast.makeText(applicationContext,"On button click :" +
//                        " ${radio.text}",
//                    Toast.LENGTH_SHORT).show()
//            }else{
//                // If no radio button checked in this radio group
//                Toast.makeText(applicationContext,"On button click :" +
//                        " nothing selected",
//                    Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//    // Get the selected radio button text using radio button on click listener
//    fun radio_button_click(view: View){
//        // Get the clicked radio button instance
//        val radio: RadioButton = findViewById(binding.radioGroup.checkedRadioButtonId)
//        Toast.makeText(applicationContext,"On click : ${radio.text}",
//            Toast.LENGTH_SHORT).show()
//    }
//        val intSelectButton: Int =
//            binding.radioGroup!!.checkedRadioButtonId
//        radioButton = radioButton?.findViewById(intSelectButton)
//        Toast.makeText(baseContext, radioButton?.text ?:"", Toast.LENGTH_SHORT).show()
//    }
    }
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
//                                    this@SchoolTypeActivity,
//                                    RecyclerView.VERTICAL,
//                                    false
//                                )
//                                adapter =
//                                    SchoolListAdapter(schoolList, object : ISchoolListItemClick {
//                                        override fun onSchoolListItemClick(
//                                            schoolId: Int?,
//                                            name: String?,
//                                            address: String?
//                                        ) {
//                                            //  showTaskFormListDialog(schoolId,name,address)
//                                        }
//                                    })
//                            }
//                        }
//                    }
//                    Status.ERROR -> {
//                        listener?.hideProgress()
//                        response.message?.let {
//                            showAppAlert(this, "Alert", it, null)
//                            binding.rclySchoolList.visibility = View.GONE
//                            binding.tvNoResult.visibility = View.VISIBLE
//                        }
//                    }
//                }
//            }
    }
//}
