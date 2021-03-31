package com.razzaghi.clockifyapi.model

import com.google.gson.annotations.SerializedName

data class Workspace(


    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("hourlyRate") val hourlyRate : HourlyRate,
    @SerializedName("memberships") val memberships : List<Memberships>,
    @SerializedName("workspaceSettings") val workspaceSettings : WorkspaceSettings,
    @SerializedName("imageUrl") val imageUrl : String,
    @SerializedName("featureSubscriptionType") val featureSubscriptionType : String


)


data class WorkspaceSettings (

    @SerializedName("timeRoundingInReports") val timeRoundingInReports : Boolean,
    @SerializedName("onlyAdminsSeeBillableRates") val onlyAdminsSeeBillableRates : Boolean,
    @SerializedName("onlyAdminsCreateProject") val onlyAdminsCreateProject : Boolean,
    @SerializedName("onlyAdminsSeeDashboard") val onlyAdminsSeeDashboard : Boolean,
    @SerializedName("defaultBillableProjects") val defaultBillableProjects : Boolean,
    @SerializedName("lockTimeEntries") val lockTimeEntries : String,
    @SerializedName("round") val round : Round,
    @SerializedName("projectFavorites") val projectFavorites : Boolean,
    @SerializedName("canSeeTimeSheet") val canSeeTimeSheet : Boolean,
    @SerializedName("canSeeTracker") val canSeeTracker : Boolean,
    @SerializedName("projectPickerSpecialFilter") val projectPickerSpecialFilter : Boolean,
    @SerializedName("forceProjects") val forceProjects : Boolean,
    @SerializedName("forceTasks") val forceTasks : Boolean,
    @SerializedName("forceTags") val forceTags : Boolean,
    @SerializedName("forceDescription") val forceDescription : Boolean,
    @SerializedName("onlyAdminsSeeAllTimeEntries") val onlyAdminsSeeAllTimeEntries : Boolean,
    @SerializedName("onlyAdminsSeePublicProjectsEntries") val onlyAdminsSeePublicProjectsEntries : Boolean,
    @SerializedName("trackTimeDownToSecond") val trackTimeDownToSecond : Boolean,
    @SerializedName("projectGroupingLabel") val projectGroupingLabel : String,
    @SerializedName("adminOnlyPages") val adminOnlyPages : List<String>,
    @SerializedName("automaticLock") val automaticLock : String,
    @SerializedName("onlyAdminsCreateTag") val onlyAdminsCreateTag : Boolean,
    @SerializedName("onlyAdminsCreateTask") val onlyAdminsCreateTask : Boolean,
    @SerializedName("timeTrackingMode") val timeTrackingMode : String,
    @SerializedName("isProjectPublicByDefault") val isProjectPublicByDefault : Boolean
)

data class Round (

    @SerializedName("round") val round : String,
    @SerializedName("minutes") val minutes : Int
)

data class Memberships (

    @SerializedName("userId") val userId : String,
    @SerializedName("hourlyRate") val hourlyRate : String,
    @SerializedName("costRate") val costRate : String,
    @SerializedName("targetId") val targetId : String,
    @SerializedName("membershipType") val membershipType : String,
    @SerializedName("membershipStatus") val membershipStatus : String
)

data class HourlyRate (

    @SerializedName("amount") val amount : Int,
    @SerializedName("currency") val currency : String
)
