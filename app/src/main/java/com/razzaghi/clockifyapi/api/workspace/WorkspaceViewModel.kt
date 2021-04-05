package com.razzaghi.clockifyapi.api.workspace

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.razzaghi.clockifyapi.api.ClockifyRepository
import com.razzaghi.clockifyapi.model.Workspace
import com.razzaghi.clockifyapi.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class WorkspaceViewModel @ViewModelInject constructor(private val clockifyRepository: ClockifyRepository) :
    ViewModel() {

    val TAG = "WorkspaceViewModel"

    val workspaces: MutableLiveData<Resource<List<Workspace>>> = MutableLiveData()
    var clockifyResponse: List<Workspace>? = null

    fun getWorkspaces(API_KEY: String) = viewModelScope.launch {
        workspaces.postValue(Resource.Loading())
        val response = clockifyRepository.getWorkspaces(API_KEY)
        workspaces.postValue(handleWorkspaceResponse(response))
    }


    private fun handleWorkspaceResponse(response: Response<List<Workspace>>): Resource<List<Workspace>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (clockifyResponse == null) {
                    clockifyResponse = resultResponse
                }/*else{
                    val oldArticles = breakNewsResponse?.articles
                    val newArticles= resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }*/
                return Resource.Success(clockifyResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


}