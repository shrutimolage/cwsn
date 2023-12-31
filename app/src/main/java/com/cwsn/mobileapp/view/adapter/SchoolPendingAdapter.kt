package com.cwsn.mobileapp.view.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowClusterSchoolItemLayoutBinding
import com.cwsn.mobileapp.model.school.SchoolData
import com.cwsn.mobileapp.view.callback.ISchoolListItemClick

class SchoolPendingAdapter(private val schoolList:List<SchoolData>,
                           private val listener:ISchoolListItemClick):RecyclerView.Adapter<SchoolPendingAdapter.ViewHolder>()
{
    private var selectedItemPosition: Int?=null
    inner class ViewHolder(private val binding:RowClusterSchoolItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root)
    {
        fun bindItems(schoolList: SchoolData)
        {
            binding.tvSchoolName.text=schoolList.name
            binding.tvSchoolAddress.text=schoolList.address
            binding.tvTotalStudentCount.text="Total Student:- "+schoolList.studentCount.toString()
            binding.imgStartSurvey.setOnClickListener {
                listener.onSchoolListItemClick(schoolList.id, schoolList.name, schoolList.address)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=RowClusterSchoolItemLayoutBinding.inflate(LayoutInflater.from(
            parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bindItems(schoolList[position])
        holder.itemView.setOnClickListener {
            selectedItemPosition = position
            notifyDataSetChanged()

        }
        if (selectedItemPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#F2F2FF"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }
    override fun getItemCount(): Int {
        return schoolList.size
    }
}