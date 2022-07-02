package com.cwsn.mobileapp.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowClusterSchoolItemLayoutBinding
import com.cwsn.mobileapp.model.school.SchoolData
import com.cwsn.mobileapp.view.callback.ISchoolListCallback

/**
Created by  on 30,June,2022
 **/
class SchoolListAdapter(private val data:List<SchoolData>,private val listener: ISchoolListCallback):RecyclerView.Adapter<SchoolListAdapter.SchoolViewHolder>()
{
    inner class SchoolViewHolder(private val binding:RowClusterSchoolItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bindItems(schoolData: SchoolData) {
            binding.tvSchoolName.text=schoolData.name
            binding.tvSchoolAddress.text=schoolData.address
            binding.tvTotalStudentCount.text=schoolData.studentCount.toString()
            binding.imgStartSurvey.setOnClickListener {
                listener.startSchoolSurvey(schoolData.id)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        val binding=RowClusterSchoolItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SchoolViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        val schoolData = data[position]
        holder.bindItems(schoolData)
    }

    override fun getItemCount(): Int {
       return data.size
    }
}