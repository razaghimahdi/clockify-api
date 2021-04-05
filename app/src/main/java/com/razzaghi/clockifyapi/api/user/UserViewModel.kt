package com.razzaghi.clockifyapi.api.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.razzaghi.clockifyapi.api.ClockifyRepository

class UserViewModel @ViewModelInject constructor(private val repository: ClockifyRepository): ViewModel() {

     private val currentWorkspaceId: MutableLiveData<String> = MutableLiveData()
     private val currentApiKey: MutableLiveData<String> = MutableLiveData()

    val users = currentWorkspaceId.switchMap { workspaceId->
        currentApiKey.switchMap {apiKey->
            repository.getUsers(apiKey,workspaceId).cachedIn(viewModelScope)
        }
    }

    fun prepareGetUsers(workspaceId: String,apiKey: String){
        currentWorkspaceId.value = workspaceId
        currentApiKey.value = apiKey
    }


}