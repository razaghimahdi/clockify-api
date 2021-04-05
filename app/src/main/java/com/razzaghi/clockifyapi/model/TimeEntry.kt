package com.razzaghi.clockifyapi.model

import com.google.gson.annotations.SerializedName

data class TimeEntry(

    @SerializedName("id") val id: String,
    @SerializedName("description") val description: String,
    @SerializedName("tagIds") val tagIds: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("billable") val billable: Boolean,
    @SerializedName("taskId") val taskId: String,
    @SerializedName("projectId") val projectId: String,
    @SerializedName("timeInterval") val timeInterval: TimeInterval,
    @SerializedName("workspaceId") val workspaceId: String,
    @SerializedName("isLocked") val isLocked: Boolean,
    @SerializedName("customFieldValues") val customFieldValues: String
)

data class TimeInterval(

    @SerializedName("start") val start: String,
    @SerializedName("end") val end: String,
    @SerializedName("duration") val duration: String
)
