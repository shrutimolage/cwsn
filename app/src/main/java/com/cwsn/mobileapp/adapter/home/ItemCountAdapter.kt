package com.cwsn.mobileapp.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowDashboardGridItemLayoutBinding
import com.cwsn.mobileapp.model.home.ItemCount

/**
Created by  on 30,June,2022
 **/
class ItemCountAdapter(private val data:List<ItemCount>) : RecyclerView.Adapter<ItemCountAdapter.CountViewHolder>() {
    inner class CountViewHolder(private val binding: RowDashboardGridItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bindItems(itemCount: ItemCount) {
                 binding.tvFieldName.text=itemCount.itemName
                binding.tvFieldCount.text=itemCount.itemCount
                binding.imageView3.setImageResource(itemCount.itemImage)
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountViewHolder {
        val binding = RowDashboardGridItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountViewHolder, position: Int) {
            val itemCount = data[position]
            holder.bindItems(itemCount)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}