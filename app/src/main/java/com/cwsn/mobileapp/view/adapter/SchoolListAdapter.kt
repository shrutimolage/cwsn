package com.cwsn.mobileapp.view.adapter

import android.R
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowClusterSchoolItemLayoutBinding
import com.cwsn.mobileapp.model.school.SchoolData
import com.cwsn.mobileapp.view.activity.QuestionActivity
import com.cwsn.mobileapp.view.activity.UserProfileActivity
import com.cwsn.mobileapp.view.callback.ISchoolListItemClick
import com.cwsn.mobileapp.view.fragment.QuestionListFragment
import com.gun0912.tedpermission.provider.TedPermissionProvider.context


class SchoolListAdapter(private val schoolList:List<SchoolData>,
                        private val listener:ISchoolListItemClick,private  val formatid:Int):RecyclerView.Adapter<SchoolListAdapter.ViewHolder>()
{

    inner class ViewHolder(private val binding:RowClusterSchoolItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root)
    {
        fun bindItems(schoolList: SchoolData)
        {
            binding.tvSchoolName.text=schoolList.name
            binding.tvSchoolAddress.text==schoolList.address
            binding.tvTotalStudentCount.text="Total Student:- "+schoolList.studentCount.toString()
            binding.imgStartSurvey.setOnClickListener {
                listener.onSchoolListItemClick(schoolList.id,schoolList.name,schoolList.address)
                val intent  = Intent (context,QuestionActivity::class.java)
                intent.putExtra("schoolname",schoolList.name)
                intent.putExtra("schooladress",schoolList.address)
                intent.putExtra("id",schoolList.id)
                intent.putExtra("formatid",formatid)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent)



            }
        }

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=RowClusterSchoolItemLayoutBinding.inflate(LayoutInflater.from(
            parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(schoolList[position])
    }

    override fun getItemCount(): Int {
        return schoolList.size
    }
}