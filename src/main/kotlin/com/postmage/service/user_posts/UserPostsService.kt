package com.postmage.service.user_posts

import com.postmage.model.posts.add_posts.AddPostModel
import com.postmage.model.posts.get_posts.GetUserPostModel
import com.postmage.mongo_client.MongoInitialize
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
            creationTime = body.creationTime
        )
        collection.insertOne(model)
        return ResponseData.success(true)
    }
}