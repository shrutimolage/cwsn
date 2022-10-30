package com.cwsn.mobileapp.view.adapter

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.RowSchoolSurveySaveBinding
import com.cwsn.mobileapp.model.questions.QuestionData
import com.cwsn.mobileapp.utils.LoggerUtils
import com.cwsn.mobileapp.view.callback.IQuestListInterface

class QuestionListAdapter(private var datalist:MutableList<QuestionData>, private val listener: IQuestListInterface):RecyclerView.Adapter<QuestionListAdapter.ViewHolder>()
{
    inner class ViewHolder(private val binding:RowSchoolSurveySaveBinding) : RecyclerView.ViewHolder(
        binding.root){
        @SuppressLint("SetTextI18n")
        fun bindItems(
            questionData: QuestionData,
            srlNum: Int,
            position: Int,
            listener: IQuestListInterface
        ) {
            binding.questData=questionData
            when(questionData.type){
                "multi_file"->{
                    binding.llMcqQuestion.visibility= View.GONE
                    binding.llTextQuestion.visibility=View.GONE
                    binding.llFileUploadArea.visibility=View.VISIBLE
                    questionData.question?.let {
                        binding.tvFileQuestion.text=it
                        binding.tvFileQuestNum.text="(Q$srlNum)"
                    }
                }
                "file"->{
                    binding.llMcqQuestion.visibility= View.GONE
                    binding.llTextQuestion.visibility=View.GONE
                    binding.llFileUploadArea.visibility=View.VISIBLE
                    questionData.question?.let {
                        binding.tvFileQuestion.text=it
                        binding.tvFileQuestNum.text="(Q$srlNum)"
                    }
                }
                "text"->{
                    binding.llMcqQuestion.visibility= View.GONE
                    binding.llTextQuestion.visibility=View.VISIBLE
                    binding.llFileUploadArea.visibility=View.GONE
                    questionData.question?.let {
                        binding.tvTextQuestion.text=it
                        binding.tvTextQuestNum.text="(Q$srlNum)"
                    }
                }
                "mcq"->{
                    binding.llMcqQuestion.visibility= View.VISIBLE
                    binding.llTextQuestion.visibility=View.GONE
                    binding.llFileUploadArea.visibility=View.GONE
                    questionData.question?.let {
                        binding.tvMcqQuestion.text=it
                        binding.tvMcqQuestionNum.text="(Q$srlNum)"
                    }
                }
            }
        }

    }

    private fun getTextFromField(edtField: EditText): String {
        return edtField.text.toString().trim()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=RowSchoolSurveySaveBinding.inflate(LayoutInflater.from(parent.context)
        ,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val srlNum=position+1
        holder.bindItems(datalist[position],srlNum,position,listener)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    fun refreshQuestionList(datalist: MutableList<QuestionData>, position: Int){
        this.datalist=datalist
        notifyItemChanged(position)
    }
}