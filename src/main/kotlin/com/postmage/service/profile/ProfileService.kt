package com.postmage.service.profile

import com.mongodb.BasicDBObject
import com.postmage.model.profile.get_my_profile.GetMyProfileInfoRequestModel
import com.postmage.model.profile.user.FollowersDataModel
import com.postmage.model.profile.user.SingleFollowerDataModel
import com.postmage.mongo_client.MongoInitialize
import com.postmage.model.profile.user.UserProfileInfoModel
import com.postmage.repo.sendErrorData
import com.postmage.service.ResponseData
import com.postmage.util.AppMessages

class ProfileService(
    private val mongoDB: MongoInitialize,
    private val appMessages: AppMessages
) : ProfileInterface {
    override suspend fun getMyProfileInfo(userId: String): ResponseData<UserProfileInfoModel?> {
        val collection = mongoDB.getUserCollection
        val query = BasicDBObject("userId", userId)

        var result: ResponseData<UserProfileInfoModel?>? = null

        collection.find(query).limit(1).forEach {
            result =  ResponseData.success(it)
        }
        result?.let { return it }

        return sendErrorData(appMessages.USER_ID_NOT_BE_NULL)
    }

    override suspend fun putMyProfileInfo(userId: String, body: UserProfileInfoModel): ResponseData<Boolean> {
        val collection = mongoDB.getUserCollection
        val query = BasicDBObject("userId", userId)

        var result: ResponseData<Boolean>? = null

        collection.find(query).limit(1).forEach {
            body.group?.let { group -> it.group = group }
            body.gender?.let { gender -> it.gender = gender }
            body.phoneNumber?.let { phoneNumber -> it.phoneNumber = phoneNumber }
            body.nameSurname?.let { nameSurname -> it.nameSurname = nameSurname }
            body.followingSize?.let { followingSize -> it.followingSize = followingSize }
            body.followersSize?.let { followersSize -> it.followersSize = followersSize }
            body.profilePhotoUrl?.let { profilePhotoUrl -> it.profilePhotoUrl = profilePhotoUrl }
            collection.replaceOne(query, it)
            result = ResponseData.success(true)
        }
        result?.let { return it }

        return sendErrorData(appMessages.USER_ID_NOT_BE_NULL)
    }

    override suspend fun getMyFollowerData(
        userId: String
    ): ResponseData<ArrayList<SingleFollowerDataModel?>> {
        TODO("Not yet implemented")
    }

    override suspend fun putMyFollowerData(userId: String, body: ArrayList<SingleFollowerDataModel?>): ResponseData<Boolean> {
        TODO("Not yet implemented")
    }
}