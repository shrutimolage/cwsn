package com.cwsn.mobileapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowSchoolListItemLayoutBinding
import com.cwsn.mobileapp.model.resourceRoom.ResRoomDetails

class ResrceRoomAdapter(private val datalist:ArrayList<ResRoomDetails>) :RecyclerView.Adapter<ResrceRoomAdapter.ViewHolder>()
{
    inner class ViewHolder(private val binding:RowSchoolListItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bindItems(resRoomDetails: ResRoomDetails) {
            binding.tvFieldName.text=resRoomDetails.itemName
            binding.tvFieldCount.text=resRoomDetails.itemCount.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=RowSchoolListItemLayoutBinding.inflate(LayoutInflater.from(
            parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(datalist[position])
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}