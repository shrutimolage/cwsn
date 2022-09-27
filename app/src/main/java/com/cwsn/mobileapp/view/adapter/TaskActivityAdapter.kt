package com.cwsn.mobileapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowTaskActivityLayoutBinding
import com.cwsn.mobileapp.model.task.Tasklist
import com.cwsn.mobileapp.view.callback.ITaskActivityCallback

class TaskActivityAdapter(private val datalist:List<Tasklist>,private val listener:
ITaskActivityCallback):RecyclerView.Adapter<TaskActivityAdapter.ViewHolder>()
{
    inner class ViewHolder(private val binding:RowTaskActivityLayoutBinding):
        RecyclerView.ViewHolder(binding.root)
    {
        fun bindItems(tasklist: Tasklist, listener: ITaskActivityCallback) {
            binding.tvFieldName.text=tasklist.name
            binding.llTaskActivityList.setOnClickListener {
                listener.onTaskItemClicked()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=RowTaskActivityLayoutBinding.inflate(LayoutInflater.from(parent.context)
        ,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(datalist[position],listener)
    }

    override fun getItemCount(): Int {
       return datalist.size
    }
}