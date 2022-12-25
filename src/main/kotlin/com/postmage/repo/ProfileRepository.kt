package com.postmage.repo

import com.postmage.enums.PostType
import com.postmage.enums.StatusCodeUtil
import com.postmage.extensions.authToDataClass
import com.postmage.extensions.writePhotoToDisk
import com.postmage.service.ResponseData
import com.postmage.util.AppMessages
import com.postmage.model.profile.user.GetFollowersDataModel
import com.postmage.model.profile.user.SetFollowersDataModel
import com.postmage.model.profile.user.UpdateProfilePhotoModel
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

    override suspend fun putMyProfilePhoto(userId: String, body: UpdateProfilePhotoModel): ResponseData<Boolean> {
        try {
            if (body.photoBytes == null) return sendErrorData(
                appMessages.PHOTO_CANNOT_BE_EMPTY,
                statusCode = StatusCodeUtil.BAD_REQUEST,
            )

            if (body.photoName == null) return sendErrorData(
                appMessages.PHOTO_NAME_CANNOT_BE_EMPTY,
                statusCode = StatusCodeUtil.BAD_REQUEST,
            )

            val result = body.photoBytes!!.writePhotoToDisk(body.photoName!!)
            if (!result) return sendErrorData(
                appMessages.SERVER_ERROR,
                statusCode = StatusCodeUtil.SERVER_ERROR
            )


            return profileService.putMyProfilePhoto(userId.authToDataClass()!!.userId, body)
        } catch (e: Exception) {
            return sendErrorData(
                appMessages.SERVER_ERROR,
                statusCode = StatusCodeUtil.SERVER_ERROR
            )
        }
    }
}