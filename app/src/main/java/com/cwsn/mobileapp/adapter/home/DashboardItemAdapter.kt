package com.cwsn.mobileapp.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowDashboardGridOptionLayoutBinding
import com.cwsn.mobileapp.model.dashboard.DashboardItem

class DashboardItemAdapter(private val datalist:MutableList<DashboardItem>):RecyclerView.Adapter<DashboardItemAdapter.ViewHolder>()
{
    class ViewHolder(private val binding: RowDashboardGridOptionLayoutBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun bindItems(dashboardItem: DashboardItem) {
            binding.imgGridItemImage.setImageResource(dashboardItem.itemImage)
            binding.tvGridItemName.text = dashboardItem.itemName
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=RowDashboardGridOptionLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(datalist[position])
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}