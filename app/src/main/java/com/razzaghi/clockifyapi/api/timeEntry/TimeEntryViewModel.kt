package com.razzaghi.clockifyapi.api.timeEntry

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.razzaghi.clockifyapi.api.ClockifyRepository

class TimeEntryViewModel @ViewModelInject constructor(private val repository: ClockifyRepository): ViewModel() {

     private val currentWorkspaceId: MutableLiveData<String> = MutableLiveData()
     private val currentUserId: MutableLiveData<String> = MutableLiveData()
     private val currentApiKey: MutableLiveData<String> = MutableLiveData()

    val timeEntries = currentWorkspaceId.switchMap { workspaceId->
        currentApiKey.switchMap {apiKey->
            currentUserId.switchMap {userId->
                repository.getTimeEntries(apiKey,workspaceId,userId).cachedIn(viewModelScope)
            }
        }
    }

    fun prepareGetTimeEntries(workspaceId: String,apiKey: String,userId: String){
        currentWorkspaceId.value = workspaceId
        currentApiKey.value = apiKey
        currentUserId.value = userId
    }


}