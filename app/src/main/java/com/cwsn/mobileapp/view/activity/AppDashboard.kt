package com.cwsn.mobileapp.view.activity

import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.cwsn.mobileapp.databinding.ActivityAppDashboardBinding
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.view.activity.base.BaseActivity
import com.cwsn.mobileapp.viewmodel.localdb.DbViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("MoveLambdaOutsideParentheses")
class AppDashboard : BaseActivity<ActivityAppDashboardBinding>()
{

    //private val dbViewModel by viewModel<DbViewModel>()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityAppDashboardBinding {
        return ActivityAppDashboardBinding.inflate(layoutInflater)
    }

    override fun getContext(): Context {
        return this@AppDashboard
    }

    override fun onActCreate() {

    }

    override fun onActResume() {
        /*dbViewModel.getAllQuestions().observe(this, { allQuestions->
            when(allQuestions.status){
                Status.LOADING->{

                }
                Status.SUCCESS->{

                }
                Status.ERROR->{

                }
            }
        })*/
    }
}