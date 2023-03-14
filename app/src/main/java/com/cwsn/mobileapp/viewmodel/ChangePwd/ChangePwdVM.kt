package com.cwsn.mobileapp.viewmodel.ChangePwd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.cwsn.mobileapp.model.login.*
import com.cwsn.mobileapp.model.profile.ChangePwdInput
import com.cwsn.mobileapp.network.Resource
import com.cwsn.mobileapp.repository.impl.IChangePwdRepos
import com.cwsn.mobileapp.utils.Utils
import kotlinx.coroutines.Dispatchers

class ChangePwdVM(private val respos: IChangePwdRepos) : ViewModel() {


    fun resetPassword(token:String, password: String, conform_password: String)
            = liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                val response = respos.resetPassword(
                    ChangePwdInput(
                        token,
                        password,
                        conform_password
                    )
                )
                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        if (response.isSuccessful) {

                            response.message()

                            emit(Resource.success(data = response, message = Utils.API_SUCCESS))
                        } else {
                            emit(
                                Resource.error(
                                    data = response,
                                    message = "Unauthorized user.Please contact admin"
                                )
                            )
                        }
                    }

                } else {
                    emit(Resource.error(data = null, message = "Server Error"))
                }
            } catch (ex: Exception) {
                emit(Resource.error(data = null, message = ex.message ?: "Error while API Call"))
            }
        }
        }
