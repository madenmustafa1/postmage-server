package com.postmage.service.profile

import com.postmage.model.profile.user.GetFollowersDataModel
import com.postmage.model.profile.user.SetFollowersDataModel
import com.postmage.model.profile.user.UpdateProfilePhotoModel
import com.postmage.model.profile.user.UserProfileInfoModel
import com.postmage.service.ResponseData

interface ProfileInterface {

    suspend fun getMyProfileInfo(userId: String): ResponseData<UserProfileInfoModel?>

    suspend fun putMyProfileInfo(userId: String, body: UserProfileInfoModel): ResponseData<Boolean>

    suspend fun getMyFollowerData(userId: String): ResponseData<GetFollowersDataModel>

    suspend fun putMyFollowerData(userId: String, body: SetFollowersDataModel): ResponseData<Boolean>

    suspend fun putMyProfilePhoto(userId: String, body: UpdateProfilePhotoModel): ResponseData<Boolean>

}