package com.cwsn.mobileapp.view.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.get
import androidx.navigation.ui.NavigationUI
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.AppDashboardLayoutBinding
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.Utils
import com.cwsn.mobileapp.utils.toast
import com.cwsn.mobileapp.view.base.BaseActivity
import com.cwsn.mobileapp.view.callback.HomeFragCallback
import com.cwsn.mobileapp.viewmodel.localdb.DbViewModel
import com.google.android.material.navigation.NavigationBarView
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("MoveLambdaOutsideParentheses")
class AppDashboard : BaseActivity<AppDashboardLayoutBinding>(), HomeFragCallback {
    private lateinit var appPreferences: AppPreferences
    private val dbViewModel by viewModel<DbViewModel>()
    private lateinit var navController: NavController

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
        NavigationUI.setupWithNavController(binding.bottomNav,navController)
        binding.bottomNav.setOnItemSelectedListener(object:NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.school->{
                        if (!isHomeFragment()) {
                            navController.popBackStack(R.id.homeFragment,true)
                            navController.navigate(R.id.homeFragment)
                        }
                    }
                    R.id.resourceRoom->
                    {
                        navController.navigate(R.id.resourceRoomFrag)
                    }
                    R.id.grievance->{
                        navController.navigate(R.id.monitoringFragment)
                    }
                }
                return true
            }

        })
        /*appPreferences = AppPreferences(getContext())
        binding.toolbar.cimgProfileIcon.setOnClickListener {
            gotoUserProfileScreen()
        }
        binding.toolbar.imgLogoutApp.setOnClickListener {
            dbViewModel.performAppLogout().observe(this, { sessionStatus ->
                when (sessionStatus.status) {
                    Status.LOADING -> {

                    }
                    Status.SUCCESS -> {
                        sessionStatus.message?.let {
                            val intent = Intent(getContext(), LoginSignUpAct::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                    }
                    Status.ERROR -> {
                        sessionStatus.message?.let {
                            toast(it)
                        }
                    }
                }
            })
        }*/

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

    override fun showProgress() {
        showProgressDialog()
    }

    override fun hideProgress() {
        hideProgressDialog()
    }
}