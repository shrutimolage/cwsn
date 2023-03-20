package com.cwsn.mobileapp.view.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowSchoolListItemLayoutBinding
import com.cwsn.mobileapp.model.school.SchoolCountData

class SchoolAllDataAdapter(private val datalist:ArrayList<SchoolCountData>):RecyclerView.Adapter<SchoolAllDataAdapter.ViewHolder>()
{
    private var selectedItemPosition: Int?=null
    inner class ViewHolder(private val binding:RowSchoolListItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bindItems(schoolDetails: SchoolCountData) {
            binding.tvFieldName.text=schoolDetails.itemName
            binding.tvFieldCount.text= schoolDetails.itemCount.toString()
            schoolDetails.itemImage?.let {
                binding.imgItemImage.setImageResource(it)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=RowSchoolListItemLayoutBinding.inflate(LayoutInflater.from(parent.context),
        parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.bindItems(datalist[position])
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
        return datalist.size
    }

}