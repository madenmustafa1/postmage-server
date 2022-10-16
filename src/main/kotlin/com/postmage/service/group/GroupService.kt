package com.postmage.service.group

import com.mongodb.BasicDBObject
import com.postmage.enums.StatusCodeUtil
import com.postmage.model.group.CreateGroupRequestModel
import com.postmage.model.group.GroupInfoModel
import com.postmage.model.posts.get_posts.GetUserPostModel
import com.postmage.mongo_client.MongoInitialize
import com.postmage.repo.sendErrorData
import com.postmage.service.ResponseData
import com.postmage.util.AppMessages
import org.bson.types.ObjectId

class GroupService(
    private val mongoDB: MongoInitialize,
    private val appMessages: AppMessages
): GroupInterface  {
    override suspend fun createGroup(userId: String, body: CreateGroupRequestModel): ResponseData<GroupInfoModel> {
        val model = GroupInfoModel(
            groupId = ObjectId.get().toString(),
            photoName = body.photoName!!,
            description = body.description,
            creationTime = body.creationTime,
            groupMembers = arrayListOf(),
            groupName = body.groupName!!,
            adminIds = arrayListOf(userId),
        )

        mongoDB.getGroupsCollection.insertOne(model)

        //Set <User> collection
        var isSuccess = false
        val query = BasicDBObject("userId", userId)
        mongoDB.getUserCollection.find(query).limit(1).forEach { userModel ->
            userModel.groups?.add(model)
            mongoDB.getUserCollection.replaceOne(query, userModel)
            isSuccess = true
        }

        if (isSuccess) return ResponseData.success(model)

        return sendErrorData(appMessages.USER_NOT_FOUND, StatusCodeUtil.BAD_REQUEST)
    }
}