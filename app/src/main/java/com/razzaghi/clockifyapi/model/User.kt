package com.razzaghi.clockifyapi.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id : String,
    @SerializedName("email") val email : String,
    @SerializedName("name") val name : String,
    @SerializedName("memberships") val memberships : List<Memberships>,
    @SerializedName("profilePicture") val profilePicture : String,
    @SerializedName("activeWorkspace") val activeWorkspace : String,
    @SerializedName("defaultWorkspace") val defaultWorkspace : String,
    @SerializedName("settings") val settings : Settings,
    @SerializedName("status") val status : String
    )


data class Settings (

    @SerializedName("weekStart") val weekStart : String,
    @SerializedName("timeZone") val timeZone : String,
    @SerializedName("timeFormat") val timeFormat : String,
    @SerializedName("dateFormat") val dateFormat : String,
    @SerializedName("sendNewsletter") val sendNewsletter : Boolean,
    @SerializedName("weeklyUpdates") val weeklyUpdates : Boolean,
    @SerializedName("longRunning") val longRunning : Boolean,
    @SerializedName("timeTrackingManual") val timeTrackingManual : Boolean,
    @SerializedName("summaryReportSettings") val summaryReportSettings : SummaryReportSettings,
    @SerializedName("isCompactViewOn") val isCompactViewOn : Boolean,
    @SerializedName("dashboardSelection") val dashboardSelection : String,
    @SerializedName("dashboardViewType") val dashboardViewType : String,
    @SerializedName("dashboardPinToTop") val dashboardPinToTop : Boolean,
    @SerializedName("projectListCollapse") val projectListCollapse : Int,
    @SerializedName("collapseAllProjectLists") val collapseAllProjectLists : Boolean,
    @SerializedName("groupSimilarEntriesDisabled") val groupSimilarEntriesDisabled : Boolean,
    @SerializedName("myStartOfDay") val myStartOfDay : String,
    @SerializedName("projectPickerTaskFilter") val projectPickerTaskFilter : Boolean
)

data class SummaryReportSettings (

    @SerializedName("group") val group : String,
    @SerializedName("subgroup") val subgroup : String
)