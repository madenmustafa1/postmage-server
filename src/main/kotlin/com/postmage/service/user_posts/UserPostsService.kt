package com.postmage.service.user_posts

import com.mongodb.BasicDBObject
import com.postmage.model.posts.add_posts.AddPostModel
import com.postmage.model.posts.get_posts.GetUserPostModel
import com.postmage.model.profile.user.GetFollowersDataModel
import com.postmage.mongo_client.MongoInitialize
import com.postmage.repo.sendErrorData
import com.postmage.service.ResponseData
import com.postmage.util.AppMessages
import org.bson.types.ObjectId

class UserPostsService(
    private val mongoDB: MongoInitialize,
    private val appMessages: AppMessages
) : UserPostsInterface {
    override suspend fun addPost(userId: String, body: AddPostModel): ResponseData<Boolean> {
        val collection = mongoDB.getUsersPostsCollection
        val model = GetUserPostModel(
            groupId = body.groupId,
            photoName = body.photoName,
            description = body.description,
            objectId = ObjectId.get().toString(),
            creationTime = body.creationTime,
            userId = userId
        )
        collection.insertOne(model)
        return ResponseData.success(true)
    }

    override suspend fun getMyPost(userId: String): ResponseData<List<GetUserPostModel>> {
        val collection = mongoDB.getUsersPostsCollection

        val query = BasicDBObject("userId", userId)
        val userPostList = arrayListOf<GetUserPostModel>()


        collection.find(query).forEach {
            userPostList.add(it)
        }


        return ResponseData.success(userPostList)
    }
}

/*
  result = ResponseData.success(
                GetFollowersDataModel(
                    following = it.following,
                    followers = it.followers,
                )
            )
 */