package com.cwsn.mobileapp.adapter.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowDashboardGridOptionLayoutBinding
import com.cwsn.mobileapp.model.dashboard.DashboardItem
import com.cwsn.mobileapp.view.callback.IDashboardListCallback

class DashboardItemAdapter(private val datalist:MutableList<DashboardItem>,
private val listener:IDashboardListCallback):RecyclerView.Adapter<DashboardItemAdapter.ViewHolder>()
{    private var selectedItemPosition: Int?=null

    class ViewHolder(private val binding: RowDashboardGridOptionLayoutBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun bindItems(dashboardItem: DashboardItem, listener: IDashboardListCallback) {
            binding.imgGridItemImage.setImageResource(dashboardItem.itemImage)
            binding.tvGridItemName.text = dashboardItem.itemName
            binding.llDashboardItem.setOnClickListener {
                binding.llDashboardItem.setBackgroundColor(Color.parseColor("#F2F2FF"))
             listener.onItemClicked(dashboardItem.itemName)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=RowDashboardGridOptionLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {

        if (selectedItemPosition == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#F2F2FF"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }

        holder.bindItems(datalist[position], listener)


    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}