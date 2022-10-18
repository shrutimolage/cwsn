package com.cwsn.mobileapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowTaskActivityLayoutBinding
import com.cwsn.mobileapp.model.task.TaskData
import com.cwsn.mobileapp.view.callback.ITaskActivityCallback

class TaskActivityAdapter(private val datalist:List<TaskData>, private val listener:
ITaskActivityCallback):RecyclerView.Adapter<TaskActivityAdapter.ViewHolder>()
{
    inner class ViewHolder(private val binding:RowTaskActivityLayoutBinding):
        RecyclerView.ViewHolder(binding.root)
    {
        fun bindItems(tasklist: TaskData, listener: ITaskActivityCallback) {
            binding.tvFieldName.text=tasklist.formatName
            binding.llTaskActivityList.setOnClickListener {
                listener.onTaskItemClicked(tasklist.id)
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