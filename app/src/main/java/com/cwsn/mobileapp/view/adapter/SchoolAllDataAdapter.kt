package com.cwsn.mobileapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowSchoolListItemLayoutBinding
import com.cwsn.mobileapp.model.school.SchoolCountData

class SchoolAllDataAdapter(private val datalist:ArrayList<SchoolCountData>):RecyclerView.Adapter<SchoolAllDataAdapter.ViewHolder>()
{
    inner class ViewHolder(private val binding:RowSchoolListItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bindItems(schoolDetails: SchoolCountData) {
            binding.tvFieldName.text=schoolDetails.itemName
            binding.tvFieldCount.text= schoolDetails.itemCount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=RowSchoolListItemLayoutBinding.inflate(LayoutInflater.from(parent.context),
        parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(datalist[position])
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

}