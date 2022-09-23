package com.cwsn.mobileapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowClusterSchoolItemLayoutBinding
import com.cwsn.mobileapp.model.monitoring.SchoolList
import com.cwsn.mobileapp.view.callback.ISchoolListItemClick

class SchoolListAdapter(private val schoolList:ArrayList<SchoolList>,
private val listener:ISchoolListItemClick):RecyclerView.Adapter<SchoolListAdapter.ViewHolder>()
{
    inner class ViewHolder(private val binding:RowClusterSchoolItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root)
    {
        fun bindItems(schoolList: SchoolList)
        {
            binding.tvSchoolName.text=schoolList.name
            binding.tvSchoolAddress.text=schoolList.address
            binding.tvTotalStudentCount.text=schoolList.student.toString()
            binding.imgStartSurvey.setOnClickListener {
                listener.onStartSurvery(schoolList.name)
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