package com.cwsn.mobileapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowSchoolSurveySaveBinding
import com.cwsn.mobileapp.model.questions.Questionist

class QuestionListAdapter(private val datalist:List<Questionist>):RecyclerView.Adapter<QuestionListAdapter.ViewHolder>()
{
    inner class ViewHolder(private val binding:RowSchoolSurveySaveBinding) : RecyclerView.ViewHolder(
        binding.root){
        fun bindItems(questionist: Questionist) {
            when(questionist.type){
                "multi_file"->{
                    binding.llMcqQuestion.visibility= View.GONE
                    binding.llTextQuestion.visibility=View.GONE
                    binding.llFileUploadArea.visibility=View.VISIBLE
                }
                "file"->{
                    binding.llMcqQuestion.visibility= View.GONE
                    binding.llTextQuestion.visibility=View.GONE
                    binding.llFileUploadArea.visibility=View.VISIBLE
                }
                "text"->{
                    binding.llMcqQuestion.visibility= View.GONE
                    binding.llTextQuestion.visibility=View.VISIBLE
                    binding.llFileUploadArea.visibility=View.GONE
                }
                "mcq"->{
                    binding.llMcqQuestion.visibility= View.VISIBLE
                    binding.llTextQuestion.visibility=View.GONE
                    binding.llFileUploadArea.visibility=View.GONE
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=RowSchoolSurveySaveBinding.inflate(LayoutInflater.from(parent.context)
        ,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(datalist[position])
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}