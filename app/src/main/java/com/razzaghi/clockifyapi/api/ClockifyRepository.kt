package com.razzaghi.clockifyapi.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.razzaghi.clockifyapi.api.timeEntry.ClockifyTimeEntryPagingSource
import com.razzaghi.clockifyapi.api.user.ClockifyUserPagingSource
import javax.inject.Inject

 class ClockifyRepository  @Inject constructor(private val clockifyApi: ClockifyApi){

    suspend fun getWorkspaces(API_KEY:String)=
        clockifyApi.getWorkspace(API_KEY)



     fun getUsers(API_KEY:String,workspaceId:String)=
         Pager(
             config = PagingConfig(
                 pageSize = 20,
                 maxSize = 100,
                 enablePlaceholders = true
             ),
             pagingSourceFactory = { ClockifyUserPagingSource(clockifyApi,API_KEY, workspaceId) }
         ).liveData




     fun getTimeEntries(API_KEY:String,workspaceId:String,userId:String)=
         Pager(
             config = PagingConfig(
                 pageSize = 20,
                 maxSize = 100,
                 enablePlaceholders = true
             ),
             pagingSourceFactory = { ClockifyTimeEntryPagingSource(clockifyApi,API_KEY, workspaceId,userId) }
         ).liveData




 }