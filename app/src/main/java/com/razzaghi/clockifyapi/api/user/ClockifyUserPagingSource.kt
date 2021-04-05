package com.razzaghi.clockifyapi.api.user

import androidx.paging.PagingSource
import com.razzaghi.clockifyapi.api.ClockifyApi
import com.razzaghi.clockifyapi.model.User
import retrofit2.HttpException
import java.io.IOException

private const val USER_STARTING_PAGE_INDEX = 1

class ClockifyUserPagingSource(
    private val clockifyApi: ClockifyApi,
    private val API_KEY: String,
    private val workspaceId: String
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val position = params.key ?: USER_STARTING_PAGE_INDEX

        return try {
            val response = clockifyApi.getUsers(API_KEY, workspaceId, position, params.loadSize)
            val users = response

            LoadResult.Page(
                data = users,
                prevKey = if (position == USER_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (users.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}