package com.cwsn.mobileapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowSchoolListItemLayoutBinding
import com.cwsn.mobileapp.model.resourceRoom.ResRoomDetails
import com.cwsn.mobileapp.view.callback.IResRoomListCallback

class ResrceRoomAdapter(private val datalist:ArrayList<ResRoomDetails>,
private val listener:IResRoomListCallback) :RecyclerView.Adapter<ResrceRoomAdapter.ViewHolder>()
{
    inner class ViewHolder(private val binding:RowSchoolListItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bindItems(resRoomDetails: ResRoomDetails, listener: IResRoomListCallback) {
            binding.tvFieldName.text=resRoomDetails.itemName
            binding.tvFieldCount.text=resRoomDetails.itemCount.toString()
            binding.llResRoomListItem.setOnClickListener {
                listener.onItemClick(resRoomDetails.itemName!!,resRoomDetails.itemCount!!)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=RowSchoolListItemLayoutBinding.inflate(LayoutInflater.from(
            parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(datalist[position],listener)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}