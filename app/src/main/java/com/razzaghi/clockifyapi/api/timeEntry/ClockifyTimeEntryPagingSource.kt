package com.razzaghi.clockifyapi.api.timeEntry

import androidx.paging.PagingSource
import com.razzaghi.clockifyapi.api.ClockifyApi
import com.razzaghi.clockifyapi.model.TimeEntry
import retrofit2.HttpException
import java.io.IOException

private const val TIME_ENTRY_STARTING_PAGE_INDEX = 1

class ClockifyTimeEntryPagingSource(
    private val clockifyApi: ClockifyApi,
    private val API_KEY: String,
    private val workspaceId: String,
    private val userId: String,
) : PagingSource<Int, TimeEntry>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TimeEntry> {
        val position = params.key ?: TIME_ENTRY_STARTING_PAGE_INDEX

        return try {
            val response = clockifyApi.getTimeEntries(API_KEY, workspaceId,userId, position, params.loadSize)
            val users = response

            LoadResult.Page(
                data = users,
                prevKey = if (position == TIME_ENTRY_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (users.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}