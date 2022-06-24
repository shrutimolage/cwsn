package com.cwsn.mobileapp.view.activity

import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cwsn.mobileapp.databinding.ActivityAppDashboardBinding
import com.cwsn.mobileapp.local.dao.QuestionDao
import com.cwsn.mobileapp.local.database.QuestionDatabase
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.repository.IAllQuestRepository
import com.cwsn.mobileapp.repository.impl.AllQuestRepository
import com.cwsn.mobileapp.utils.toast
import com.cwsn.mobileapp.view.activity.base.BaseActivity
import com.cwsn.mobileapp.viewmodel.localdb.DbVMFactory
import com.cwsn.mobileapp.viewmodel.localdb.DbViewModel
import nl.joery.animatedbottombar.AnimatedBottomBar
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("MoveLambdaOutsideParentheses")
class AppDashboard : BaseActivity<ActivityAppDashboardBinding>()
{
    private lateinit var questionDao: QuestionDao
    private lateinit var repos:IAllQuestRepository
    private lateinit var factory:DbVMFactory
    private lateinit var viewModel: DbViewModel

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityAppDashboardBinding {
        return ActivityAppDashboardBinding.inflate(layoutInflater)
    }

    override fun getContext(): Context {
        return this@AppDashboard
    }

    override fun onActCreate() {
        val appDatabase=QuestionDatabase.getInstance(getContext())
        questionDao=appDatabase.questionDao()
        repos=AllQuestRepository(questionDao)
        factory= DbVMFactory(repos)
        viewModel= ViewModelProvider(this,factory)[DbViewModel::class.java]
        binding.layoutDashboard.bottomNavBar.animatedBottomBar.setOnTabSelectListener(object:AnimatedBottomBar.OnTabSelectListener{
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when(newIndex){
                    0->{
                        toast("home")
                    }
                    1->{
                        toast("summary")
                    }
                    2->{
                        toast("grievance")
                    }
                }
            }

        })
    }

    override fun onActResume() {
        viewModel.getAllQuestions().observe(this, { questions->
            when(questions.status){
                Status.SUCCESS->{
                    toast("Success")
                }
                Status.ERROR->{
                    questions.message?.let {
                        toast(it)
                    }
                }
                Status.LOADING->{
                    toast("loading")
                }
            }
        })
    }
}