package com.cwsn.mobileapp.viewmodel.localdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cwsn.mobileapp.local.table.AllQuestion
import com.cwsn.mobileapp.network.Resource
import com.cwsn.mobileapp.repository.IAllQuestRepository
import com.cwsn.mobileapp.repository.impl.AllQuestRepository
import kotlinx.coroutines.Dispatchers

/**
Created by  on 22,June,2022
 **/
class DbViewModel(private val questRepository: IAllQuestRepository):ViewModel()
{
    fun performSavingQuestions(questionList:List<AllQuestion>) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
             questRepository.saveAllQuestion(questionList)
             emit(Resource.success(data = null, message = "Done"))
        }
        catch (ex:Exception){
            emit(Resource.error(data = null, message = "Error while saving question"))
        }
    }

    fun getAllQuestions() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val allLocalQuestionData = questRepository.getAllLocalQuestionData()
            if(allLocalQuestionData.isNotEmpty()){
                emit(Resource.success(data = allLocalQuestionData,message="All Question"))
            }else{
                emit(Resource.error(data = null, message = "No question found"))
            }
        }
        catch (ex:Exception){
            emit(Resource.error(data = null, message = "Error while getting questions"))
        }
    }
}