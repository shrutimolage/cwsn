package com.cwsn.mobileapp.adapter.surveyQuestion

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cwsn.mobileapp.databinding.RowSchoolSurveySaveBinding
import com.cwsn.mobileapp.model.questions.LocalSurveyQuestion
import com.cwsn.mobileapp.utils.LoggerUtils
import com.cwsn.mobileapp.utils.toast
import com.cwsn.mobileapp.view.callback.IQuestListInterface

class AllQuestionListAdapter(private val _datalist:List<LocalSurveyQuestion>,private val listener: IQuestListInterface):RecyclerView.Adapter<AllQuestionListAdapter.QuestionViewHolder>()
{
    private var _context: Context?=null

    inner class QuestionViewHolder(private val binding:RowSchoolSurveySaveBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindItems(
            allQuestion: LocalSurveyQuestion,
            listener: IQuestListInterface,
            _context: Context?
        ) {
            allQuestion.questionType?.let { questType->
                if(questType == "radio" || questType == "mcq"){
                    binding.imgAddSaveAnswer.visibility=View.GONE
                    binding.llMcqQuestion.visibility=View.VISIBLE
                    binding.llTextQuestion.visibility=View.GONE
                    if(allQuestion.mcqOptionList==null){
                        listener.updateMCQOptionForId(allQuestion)
                    }
                    allQuestion.mcqOptionList?.let { datalist->
                        if(datalist.size>0){
                            binding.rclyMcqOptions.apply {
                                layoutManager=LinearLayoutManager(_context,RecyclerView.VERTICAL,false)
                                adapter=MCQQuestAdapter(datalist)
                            }
                        }
                    }
                }
                else {
                    binding.llMcqQuestion.visibility=View.GONE
                    binding.llTextQuestion.visibility=View.VISIBLE
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        _context=parent.context
        val binding=RowSchoolSurveySaveBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return QuestionViewHolder((binding))
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val allQuestion = _datalist[position]
        holder.bindItems(allQuestion,listener,_context)
    }

    override fun getItemCount(): Int {
        return _datalist.size
    }

    fun refreshAdapter() {
        notifyDataSetChanged()
    }
}