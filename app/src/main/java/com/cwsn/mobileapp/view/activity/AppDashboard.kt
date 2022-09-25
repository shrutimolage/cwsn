package com.cwsn.mobileapp.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.get
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.AppDashboardLayoutBinding
import com.cwsn.mobileapp.model.home.SlideModel
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.Utils
import com.cwsn.mobileapp.utils.navigateSafe
import com.cwsn.mobileapp.utils.toast
import com.cwsn.mobileapp.view.adapter.SlidePanelAdapter
import com.cwsn.mobileapp.view.base.BaseActivity
import com.cwsn.mobileapp.view.callback.*
import com.cwsn.mobileapp.viewmodel.localdb.DbViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("MoveLambdaOutsideParentheses")
class AppDashboard : BaseActivity<AppDashboardLayoutBinding>(), IHomeFragCallback ,
ISchoolListCallback,IResourceRoomCallback, IMonitoringFragCallback {
    private lateinit var appPreferences: AppPreferences
    private val dbViewModel by viewModel<DbViewModel>()
    private lateinit var navController: NavController
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private lateinit var drawerList: MutableList<SlideModel>
    private lateinit var drawerAdapter: SlidePanelAdapter



    override fun inflateLayout(layoutInflater: LayoutInflater): AppDashboardLayoutBinding {
        return AppDashboardLayoutBinding.inflate(layoutInflater)
    }

    override fun getContext(): Context {
        return this@AppDashboard
    }

    override fun isSetUpProgressDialog(): Boolean {
        return true
    }

    override fun onActCreate() {
        navController = Navigation.findNavController(this, R.id.app_nav_host_fragment)
        NavigationUI.setupWithNavController(binding.navTopView,navController)
        setUpNavigationDrawer()

    }

    override fun toggleAppTopBar() {
        toggleDrawer()
    }
    private fun toggleDrawer() {
        binding.drawerLayout.openDrawer(binding.navTopView,true)
    }

    private fun setUpNavigationDrawer() {
        mDrawerToggle= ActionBarDrawerToggle(this,binding.drawerLayout,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        mDrawerToggle?.syncState()
        mDrawerToggle?.let {
            binding.drawerLayout.addDrawerListener(it)
        }
    }

    private fun isHomeFragment(): Boolean {
        return with(navController) {
            currentDestination == graph[R.id.homeFragment]
        }
    }

    private fun loadGrievanceFragment() {
        /*val fragmentTransaction = supportFragmentManager.beginTransaction()
        val grievanceFragment = GrievanceFragment.newInstance("", "")
        fragmentTransaction.replace(R.id.ll_fragContainer, grievanceFragment, GrievanceFragment.TAG)
        fragmentTransaction.commit()*/
    }

    private fun loadSummaryFragment() {
        /*val fragmentTransaction = supportFragmentManager.beginTransaction()
        val summaryFragment = SurveyFragment.newInstance("", "")
        fragmentTransaction.replace(R.id.ll_fragContainer, summaryFragment, SurveyFragment.TAG)
        fragmentTransaction.commit()*/
    }
    
    private fun gotoUserProfileScreen() {
        val profileIntent = Intent(getContext(), UserProfileActivity::class.java)
        startActivity(profileIntent)
    }

    override fun gotoSchoolSurvey(schoolId: Int?, name: String?, address: String?) {
        dbViewModel.getAllSurveyQuestions(schoolId).observe(this, { response->
            when(response.status){
                Status.SUCCESS->{
                    hideProgressDialog()
                    val surveyIntent=Intent(getContext(),SurveyStartActivity::class.java)
                    surveyIntent.putExtra(Utils.SCHOOL_ID,schoolId)
                    surveyIntent.putExtra(Utils.SCHOOL_NAME,name)
                    surveyIntent.putExtra(Utils.SCHOOL_ADDRS,address)
                    startActivity(surveyIntent)
                }
                Status.ERROR->{
                    hideProgressDialog()
                    response.message?.let{
                        toast(it)
                    }
                }
                Status.LOADING->{
                    showProgressDialog()
                }
            }
        })
    }

    private fun loadHomeFragment() {
      /*  val fragmentTransaction = supportFragmentManager.beginTransaction()
        val homeFragment = HomeFragment.newInstance("", "")
        fragmentTransaction.replace(R.id.ll_fragContainer, homeFragment, HomeFragment.TAG)
        fragmentTransaction.commit()*/
    }

    override fun onActResume() {
        //getLocalQuestions()
        drawerList= Utils.generateSlidePanelItems()
        binding.navigationView.rclvSideDrawer.apply {
            layoutManager= LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            drawerAdapter= SlidePanelAdapter(drawerList,object:IDrawerItemCallback{
                override fun onItemClicked(itemName: String) {
                    drawerNavigation(itemName)
                }
            })
            adapter = drawerAdapter
        }
    }

    private fun drawerNavigation(itemName: String) {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        when (itemName) {
            "Home"->{
                if (!isHomeFragment()) {
                    navController.popBackStack(R.id.homeFragment,true)
                    navController.navigate(R.id.homeFragment)
                }
            }
            "Resource Room"->{
                gotoResourceRoom()
            }
            "Monitoring"->{
                gotoMonitoring()
            }
        }
    }

    private fun gotoMonitoring() {
        navController.navigateSafe(R.id.action_homeFragment_to_monitoringFragment,null,null,null)
    }

    private fun gotoResourceRoom() {
        navController.navigateSafe(R.id.action_homeFragment_to_resourceRoomFrag,null,null,null)
    }


    private fun getLocalQuestions() {
        dbViewModel.getAllQuestions().observe(this, { questions ->
            when (questions.status) {
                Status.SUCCESS -> {
                    hideProgressDialog()
                    toast("Success ${questions.data?.size}")
                }
                Status.ERROR -> {
                    hideProgressDialog()
                    questions.message?.let {
                        toast(it)
                    }
                }
                Status.LOADING -> {
                    showProgressDialog()
                }
            }
        })
    }

    override fun onUserBackPressed() {
        if (isHomeFragment()) {
            finish()
        }
        else{
            navController.navigateUp()
        }
    }

    override fun showProgress() {
        showProgressDialog()
    }

    override fun hideProgress() {
        hideProgressDialog()
    }

    override fun gotoTaskActivityScreen(name: String?) {
        val bundle = Bundle()
        bundle.putString(Utils.SCHOOL_NAME,name)
        navController.navigateSafe(R.id.action_monitoringFragment_to_taskActivityFragment,
        bundle,null,null)
    }

    override fun onNavigateOptionScreen(itemName: String) {
        when(itemName){
            "School"->{
                navController.navigateSafe(R.id.action_homeFragment_to_schoolDetailsFragment,null,null,null)
            }
            "Resource Room"->{
                gotoResourceRoom()
            }
            "Monitoring"->{
                gotoMonitoring()
            }
        }
    }
}