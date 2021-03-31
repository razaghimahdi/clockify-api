package com.razzaghi.clockifyapi.api

import javax.inject.Inject

 class ClockifyRepository  @Inject constructor(private val clockifyApi: ClockifyApi){

    suspend fun getWorkspaces(API_KEY:String)=
        clockifyApi.getWorkspace(API_KEY)

}