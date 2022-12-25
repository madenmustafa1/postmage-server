package com.postmage.service.profile

import com.mongodb.BasicDBObject
import com.postmage.model.group.GroupUsersModel
import com.postmage.model.profile.user.*
import com.postmage.mongo_client.MongoInitialize
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
            it.password = null
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
            body.groups?.let { group -> it.groups?.addAll(group) }
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
        val userCollection = mongoDB.getUserCollection
        val collection = mongoDB.getUserCollection
        val query = BasicDBObject("userId", userId)

        val following: ArrayList<SingleFollowerDataModel> = arrayListOf()
        val followers: ArrayList<SingleFollowerDataModel> = arrayListOf()

        collection.find(query).limit(1).forEach {
            it.following.forEach { otherUser ->
                //Get <User> collection
                val userQuery = BasicDBObject("userId", otherUser.userId)

                userCollection.find(userQuery).limit(1).forEach { user ->
                    following.add(
                        SingleFollowerDataModel(
                            nameSurname = user.nameSurname ?: "",
                            userId = user.userId ?: "",
                            photoUrl = user.profilePhotoUrl
                        )
                    )
                }
            }

            it.followers.forEach { otherUser ->
                //Get <User> collection
                val userQuery = BasicDBObject("userId", otherUser.userId)

                userCollection.find(userQuery).limit(1).forEach { user ->
                    followers.add(
                        SingleFollowerDataModel(
                            nameSurname = user.nameSurname ?: "",
                            userId = user.userId ?: "",
                            photoUrl = user.profilePhotoUrl
                        )
                    )
                }
            }
        }

        return ResponseData.success(
            GetFollowersDataModel(
                following = following,
                followers = followers,
            )
        )
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
                model.followers.add(SingleFollowerDataModel(userId = followers.userId))
            }

            body.following?.let { following ->
                model.following.removeIf { model.userId == following.userId }
                model.following.add(SingleFollowerDataModel(userId = following.userId))
            }

            collection.replaceOne(query, model)
            result = ResponseData.success(true)
        }

        result?.let { return it }

        return sendErrorData(appMessages.USER_ID_NOT_BE_NULL)
    }

    override suspend fun putMyProfilePhoto(userId: String, body: UpdateProfilePhotoModel): ResponseData<Boolean> {
        val query = BasicDBObject("userId", userId)
        var isSuccess = false

        mongoDB.getUserCollection.find(query)
            .limit(1).forEach { model ->
                if (model.userId != userId) return@forEach

                model.profilePhotoUrl = body.photoName
                mongoDB.getUserCollection.replaceOne(query, model)
                isSuccess = true
            }

        return ResponseData.success(isSuccess)
    }
}