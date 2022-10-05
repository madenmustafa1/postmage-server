package com.postmage.repo

import com.postmage.extensions.authToDataClass
import com.postmage.service.ResponseData
import com.postmage.util.AppMessages
import com.postmage.model.profile.user.GetFollowersDataModel
import com.postmage.model.profile.user.SetFollowersDataModel
import com.postmage.model.profile.user.UserProfileInfoModel
import com.postmage.service.profile.ProfileInterface
import com.postmage.service.profile.ProfileService

class ProfileRepository(
    private val profileService: ProfileService,
    private val appMessages: AppMessages
): ProfileInterface {
    override suspend fun getMyProfileInfo(userId: String): ResponseData<UserProfileInfoModel?> {
        return profileService.getMyProfileInfo(userId.authToDataClass()!!.userId)
    }

    override suspend fun putMyProfileInfo(userId: String, body: UserProfileInfoModel): ResponseData<Boolean> {
        return profileService.putMyProfileInfo(userId.authToDataClass()!!.userId, body)
    }

    override suspend fun getMyFollowerData(userId: String): ResponseData<GetFollowersDataModel> {
        return profileService.getMyFollowerData(userId.authToDataClass()!!.userId)
    }

    override suspend fun putMyFollowerData(userId: String, body: SetFollowersDataModel): ResponseData<Boolean> {
        return profileService.putMyFollowerData(userId.authToDataClass()!!.userId, body)
    }
}