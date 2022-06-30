package com.cwsn.mobileapp.view.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.ActivityAppDashboardBinding
import com.cwsn.mobileapp.local.dao.QuestionDao
import com.cwsn.mobileapp.local.database.QuestionDatabase
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.repository.IAllQuestRepository
import com.cwsn.mobileapp.repository.impl.AllQuestRepository
import com.cwsn.mobileapp.utils.AppPreferences
import com.cwsn.mobileapp.utils.toast
import com.cwsn.mobileapp.view.activity.base.BaseActivity
import com.cwsn.mobileapp.view.callback.HomeFragCallback
import com.cwsn.mobileapp.view.fragment.GrievanceFragment
import com.cwsn.mobileapp.view.fragment.HomeFragment
import com.cwsn.mobileapp.view.fragment.SummaryFragment
import com.cwsn.mobileapp.viewmodel.home.HomeViewModel
import com.cwsn.mobileapp.viewmodel.localdb.DbVMFactory
import com.cwsn.mobileapp.viewmodel.localdb.DbViewModel
import nl.joery.animatedbottombar.AnimatedBottomBar
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("MoveLambdaOutsideParentheses")
class AppDashboard : BaseActivity<ActivityAppDashboardBinding>(),HomeFragCallback
{
    private lateinit var appPreferences: AppPreferences
    private lateinit var questionDao: QuestionDao
    private lateinit var repos:IAllQuestRepository
    private lateinit var factory:DbVMFactory
    private lateinit var dbViewModel: DbViewModel

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityAppDashboardBinding {
        return ActivityAppDashboardBinding.inflate(layoutInflater)
    }

    override fun getContext(): Context {
        return this@AppDashboard
    }

    override fun isSetUpProgressDialog(): Boolean {
        return true
    }

    override fun onActCreate() {
        appPreferences= AppPreferences(getContext())
        val appDatabase=QuestionDatabase.getInstance(getContext())
        questionDao=appDatabase.questionDao()
        repos=AllQuestRepository(questionDao,appPreferences)
        factory= DbVMFactory(repos)
        dbViewModel= ViewModelProvider(this,factory)[DbViewModel::class.java]
        binding.layoutDashboard.bottomNavBar.animatedBottomBar.setOnTabSelectListener(object:AnimatedBottomBar.OnTabSelectListener{
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when(newIndex){
                    0->{
                        loadHomeFragment()
                    }
                    1->{
                        loadSummaryFragment()
                    }
                    2->{
                        //loadGrievanceFragment()
                    }
                }
            }
        })
        binding.toolbar.cimgProfileIcon.setOnClickListener {
            gotoUserProfileScreen()
        }
        binding.toolbar.imgLogoutApp.setOnClickListener {
            dbViewModel.performAppLogout().observe(this, { sessionStatus->
                when(sessionStatus.status){
                   Status.LOADING->{

                   }
                    Status.SUCCESS->{
                        sessionStatus.message?.let{
                            val intent = Intent(getContext(), LoginSignUpAct::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                    }
                    Status.ERROR->{
                        sessionStatus.message?.let {
                            toast(it)
                        }
                    }
                }
            })
        }
        loadHomeFragment()
    }

    private fun loadGrievanceFragment() {
        val fragmentTransaction=supportFragmentManager.beginTransaction()
        val grievanceFragment=GrievanceFragment.newInstance("","")
        fragmentTransaction.replace(R.id.ll_fragContainer,grievanceFragment,GrievanceFragment.TAG)
        fragmentTransaction.commit()
    }

    private fun loadSummaryFragment() {
        val fragmentTransaction=supportFragmentManager.beginTransaction()
        val summaryFragment=SummaryFragment.newInstance("","")
        fragmentTransaction.replace(R.id.ll_fragContainer,summaryFragment,SummaryFragment.TAG)
        fragmentTransaction.commit()
    }

    private fun gotoUserProfileScreen() {
        val profileIntent= Intent(getContext(),UserProfileActivity::class.java)
        startActivity(profileIntent)
    }

    private fun loadHomeFragment() {
        val fragmentTransaction=supportFragmentManager.beginTransaction()
        val homeFragment = HomeFragment.newInstance("", "")
        fragmentTransaction.replace(R.id.ll_fragContainer,homeFragment,HomeFragment.TAG)
        fragmentTransaction.commit()
    }

    override fun onActResume() {
        //getLocalQuestions()
    }



    private fun getLocalQuestions() {
        dbViewModel.getAllQuestions().observe(this, { questions ->
            when (questions.status) {
                Status.SUCCESS -> {
                    toast("Success")
                }
                Status.ERROR -> {
                    questions.message?.let {
                        toast(it)
                    }
                }
                Status.LOADING -> {
                    toast("loading")
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