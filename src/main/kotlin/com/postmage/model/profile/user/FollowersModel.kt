package com.postmage.model.profile.user

data class SingleFollowerDataModel(
    val nameSurname: String,
    val userID: String,
    val photoURL: String?
)

data class FollowersDataModel(
    val following: ArrayList<SingleFollowerDataModel>,
    val followers: ArrayList<SingleFollowerDataModel>,
    val mail: String?,
    val userID: String?
)