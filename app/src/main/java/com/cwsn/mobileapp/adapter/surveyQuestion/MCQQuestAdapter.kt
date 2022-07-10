package com.cwsn.mobileapp.adapter.surveyQuestion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowMcqSurveySaveLayoutBinding
import com.cwsn.mobileapp.local.table.MCQOptions

class MCQQuestAdapter(private val dataList:List<MCQOptions>):RecyclerView.Adapter<MCQQuestAdapter.MCQQuestViewHolder>()
{
    inner class MCQQuestViewHolder(private val binding: RowMcqSurveySaveLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bindItems(mcqOptions: MCQOptions) {
            binding.tvAnswerOption.text = mcqOptions.option
            binding.llTxtOptionAns.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MCQQuestViewHolder {
        val binding = RowMcqSurveySaveLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MCQQuestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MCQQuestViewHolder, position: Int) {
        val mcqOptions = dataList[position]
        holder.bindItems(mcqOptions)
    }

    override fun getItemCount(): Int {
       return dataList.size
    }
}