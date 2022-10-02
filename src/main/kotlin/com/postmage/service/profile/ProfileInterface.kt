package com.postmage.service.profile

import com.postmage.model.profile.get_my_profile.GetMyProfileInfoRequestModel
import com.postmage.model.profile.user.FollowersDataModel
import model.profile.user.UserProfileInfoModel
import com.postmage.service.ResponseData

interface ProfileInterface {

    suspend fun getMyProfileInfo(userId: String): ResponseData<UserProfileInfoModel?>

    suspend fun putMyProfileInfo(userId: String, body: UserProfileInfoModel): ResponseData<Boolean>

    suspend fun getMyFollowerData(userId: String, body: GetMyProfileInfoRequestModel): ResponseData<FollowersDataModel?>

    suspend fun putMyFollowerData(userId: String, body: FollowersDataModel): ResponseData<Boolean>

}