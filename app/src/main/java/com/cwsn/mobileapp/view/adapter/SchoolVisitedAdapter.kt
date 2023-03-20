package com.cwsn.mobileapp.view.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowClusterSchoolItemLayoutBinding
import com.cwsn.mobileapp.model.school.SchoolData
import com.cwsn.mobileapp.model.school.VisitedSchool
import com.cwsn.mobileapp.view.callback.ISchoolListItemClick
import java.lang.StringBuilder

class SchoolVisitedAdapter(private val schoolList:List<VisitedSchool>):RecyclerView.Adapter<SchoolVisitedAdapter.ViewHolder>()
{
    private var selectedItemPosition: Int?=null
    inner class ViewHolder(private val binding:RowClusterSchoolItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root)
    {
        fun bindItems(school: VisitedSchool)
        {
            binding.tvSchoolName.text=school.schoolName
            binding.tvSchoolAddress.text=getSchoolAddress(school)
            binding.tvTotalStudentCount.text="Total Student:- "+0
            binding.imgStartSurvey.visibility= View.GONE
        }

    }

    private fun getSchoolAddress(school: VisitedSchool): String {
        return StringBuilder().append(school.clusterName).append(", ").append(school.blockName)
            .append(", ").append(school.districtName)
            .toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=RowClusterSchoolItemLayoutBinding.inflate(LayoutInflater.from(
            parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bindItems(schoolList[position])
        holder.itemView.setOnClickListener{
            selectedItemPosition = position
            notifyDataSetChanged()

        }
        if(selectedItemPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#F2F2FF"))
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    override fun getItemCount(): Int {
        return schoolList.size
    }
}