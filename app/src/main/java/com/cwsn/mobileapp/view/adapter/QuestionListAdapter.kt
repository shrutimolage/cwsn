package com.cwsn.mobileapp.view.adapter

import android.annotation.SuppressLint
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
        @SuppressLint("SetTextI18n")
        fun bindItems(questionist: Questionist, srlNum: Int) {
            when(questionist.type){
                "multi_file"->{
                    binding.llMcqQuestion.visibility= View.GONE
                    binding.llTextQuestion.visibility=View.GONE
                    binding.llFileUploadArea.visibility=View.VISIBLE
                    questionist.question?.let {
                        binding.tvFileQuestion.text=it
                        binding.tvFileQuestNum.text="(Q$srlNum)"
                    }
                }
                "file"->{
                    binding.llMcqQuestion.visibility= View.GONE
                    binding.llTextQuestion.visibility=View.GONE
                    binding.llFileUploadArea.visibility=View.VISIBLE
                    questionist.question?.let {
                        binding.tvFileQuestion.text=it
                        binding.tvFileQuestNum.text="(Q$srlNum)"
                    }
                }
                "text"->{
                    binding.llMcqQuestion.visibility= View.GONE
                    binding.llTextQuestion.visibility=View.VISIBLE
                    binding.llFileUploadArea.visibility=View.GONE
                    questionist.question?.let {
                        binding.tvTextQuestion.text=it
                        binding.tvTextQuestNum.text="(Q$srlNum)"
                    }
                }
                "mcq"->{
                    binding.llMcqQuestion.visibility= View.VISIBLE
                    binding.llTextQuestion.visibility=View.GONE
                    binding.llFileUploadArea.visibility=View.GONE
                    questionist.question?.let {
                        binding.tvMcqQuestion.text=it
                        binding.tvMcqQuestionNum.text="(Q$srlNum)"
                    }
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
        val srlNum=position+1
        holder.bindItems(datalist[position],srlNum)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}