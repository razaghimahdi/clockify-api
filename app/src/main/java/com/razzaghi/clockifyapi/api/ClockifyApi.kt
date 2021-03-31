package com.razzaghi.clockifyapi.api


 import com.razzaghi.clockifyapi.model.ClockifyResponse
 import com.razzaghi.clockifyapi.model.Workspace
 import okhttp3.ResponseBody
 import retrofit2.Response
 import retrofit2.http.GET
 import retrofit2.http.Header
 import retrofit2.http.Headers

interface ClockifyApi {


//OWVjMjY5ZTUtMzgwOS00ZTFlLWE1M2EtMzBiOGQzYzNkNjlk
    @Headers("content-type: application/json")
     //@Headers("X-Api-Key: OWVjMjY5ZTUtMzgwOS00ZTFlLWE1M2EtMzBiOGQzYzNkNjlk")
    @GET("workspaces")
    //suspend fun getWorkspace(@Header("X-Api-Key") API_KEY:String): Response<ResponseBody>
    suspend fun getWorkspace(@Header("X-Api-Key") API_KEY:String): Response<List<Workspace>>




}