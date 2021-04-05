package com.razzaghi.clockifyapi.api


 import com.razzaghi.clockifyapi.model.TimeEntry
 import com.razzaghi.clockifyapi.model.User
 import com.razzaghi.clockifyapi.model.Workspace
 import retrofit2.Response
 import retrofit2.http.*

interface ClockifyApi {


//OWVjMjY5ZTUtMzgwOS00ZTFlLWE1M2EtMzBiOGQzYzNkNjlk
    @Headers("content-type: application/json")
     //@Headers("X-Api-Key: OWVjMjY5ZTUtMzgwOS00ZTFlLWE1M2EtMzBiOGQzYzNkNjlk")
    @GET("workspaces")
    //suspend fun getWorkspace(@Header("X-Api-Key") API_KEY:String): Response<ResponseBody>
    suspend fun getWorkspace(@Header("X-Api-Key") API_KEY:String): Response<List<Workspace>>


    @Headers("content-type: application/json")
    @GET("workspaces/{workspaceId}/users")
    suspend fun getUsers(
        @Header("X-Api-Key") API_KEY:String,
        @Path("workspaceId") workspaceId: String,
        @Query("page") page: Int,
        @Query("page-size") pageSize: Int
    ):  List<User>

    @Headers("content-type: application/json")
    @GET("workspaces/{workspaceId}/user/{userId}/time-entries")
    suspend fun getTimeEntries(
        @Header("X-Api-Key") API_KEY:String,
        @Path("workspaceId") workspaceId: String,
        @Path("userId") userId: String,
        @Query("page") page: Int,
        @Query("page-size") pageSize: Int
    ):  List<TimeEntry>


}