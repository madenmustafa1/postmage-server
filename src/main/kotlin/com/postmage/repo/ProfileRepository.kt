package repo

import extensions.authToDataClass
import model.profile.get_my_profile.GetMyProfileInfoRequestModel
import model.profile.user.FollowersDataModel
import model.profile.user.UserProfileInfoModel
import service.ResponseData
import service.profile.ProfileInterface
import service.profile.ProfileService
import util.AppMessages

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

    override suspend fun getMyFollowerData(userId: String, body: GetMyProfileInfoRequestModel): ResponseData<FollowersDataModel?> {
        TODO("Not yet implemented")
    }

    override suspend fun putMyFollowerData(userId: String, body: FollowersDataModel): ResponseData<Boolean> {
        TODO("Not yet implemented")
    }
}