package com.postmage.service.profile

import com.mongodb.BasicDBObject
import com.postmage.model.profile.user.GetFollowersDataModel
import com.postmage.model.profile.user.SetFollowersDataModel
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
            result = ResponseData.success(it)
        }
        result?.let { return it }

        return sendErrorData(appMessages.USER_ID_NOT_BE_NULL)
    }

    override suspend fun putMyProfileInfo(userId: String, body: UserProfileInfoModel): ResponseData<Boolean> {
        val collection = mongoDB.getUserCollection
        val query = BasicDBObject("userId", userId)

        var result: ResponseData<Boolean>? = null

        collection.find(query).limit(1).forEach {
            body.groups?.let { group -> it.groups = group }
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
    ): ResponseData<GetFollowersDataModel> {
        val collection = mongoDB.getUserCollection
        val query = BasicDBObject("userId", userId)

        var result: ResponseData<GetFollowersDataModel>? = null

        collection.find(query).limit(1).forEach {
            result = ResponseData.success(
                GetFollowersDataModel(
                    following = it.following,
                    followers = it.followers,
                )
            )
        }
        result?.let { return it }

        return sendErrorData(appMessages.USER_ID_NOT_BE_NULL)
    }

    override suspend fun putMyFollowerData(
        userId: String,
        body: SetFollowersDataModel
    ): ResponseData<Boolean> {
        val collection = mongoDB.getUserCollection
        val query = BasicDBObject("userId", userId)

        var result: ResponseData<Boolean>? = null

        collection.find(query).limit(1).forEach { model ->
            body.followers?.let { followers ->
                model.followers.removeIf { model.userId == followers.userId }
                model.followers.add(followers)
            }

            body.following?.let { following ->
                model.following.removeIf { model.userId == following.userId }
                model.following.add(following)
            }

            collection.replaceOne(query, model)
            result = ResponseData.success(true)
        }

        result?.let { return it }

        return sendErrorData(appMessages.USER_ID_NOT_BE_NULL)
    }
}