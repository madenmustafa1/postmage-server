package com.postmage.service.group

import com.mongodb.BasicDBObject
import com.postmage.enums.StatusCodeUtil
import com.postmage.model.group.AddUsersToGroupModel
import com.postmage.model.group.CreateGroupRequestModel
import com.postmage.model.group.GroupInfoModel
import com.postmage.model.group.GroupUsersModel
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
        var model: GroupInfoModel? = null
        var isSuccess = false

        //Get <User> collection
        val query = BasicDBObject("userId", userId)
        mongoDB.getUserCollection.find(query).limit(1).forEach { userModel ->
            model = GroupInfoModel(
                groupId = ObjectId.get().toString(),
                photoName = body.photoName!!,
                description = body.description,
                creationTime = body.creationTime,
                groupUsers = arrayListOf(
                    //Add admin
                    GroupUsersModel(
                        name = userModel.nameSurname ?: "",
                        id = userId,
                        profileUrl = userModel.profilePhotoUrl ?: ""
                    )
                ),
                groupName = body.groupName!!,
                adminIds = arrayListOf(userId),
            )
            //Set <Group> collection
            mongoDB.getGroupsCollection.insertOne(model!!)

            //Set <User> collection
            userModel.groups?.add(model!!)
            mongoDB.getUserCollection.replaceOne(query, userModel)
            isSuccess = true
        }

        if (isSuccess) return ResponseData.success(model)

        return sendErrorData(appMessages.USER_NOT_FOUND, StatusCodeUtil.BAD_REQUEST)
    }

    override suspend fun addUsersToGroup(userId: String, body: AddUsersToGroupModel): ResponseData<Boolean> {
        var isSuccess = false
        //Get <Group> collection
        val groupQuery = BasicDBObject("groupId", body.groupId)
        mongoDB.getGroupsCollection.find(groupQuery).limit(1).forEach { groupModel ->
            //Admin Control
            for (i in groupModel.adminIds) {
                if (i == userId) {
                    //Find users <User> collection
                    val userQuery = BasicDBObject("userId", body.id)
                    mongoDB.getUserCollection.find(userQuery).limit(1).forEach { userModel ->
                        groupModel.groupUsers.add(
                            //Add users
                            GroupUsersModel(
                                name = userModel.nameSurname ?: "",
                                id = userModel.userId!!,
                                profileUrl = userModel.profilePhotoUrl ?: ""
                            )
                        )
                        //Set <Group> collection
                        mongoDB.getGroupsCollection.replaceOne(groupQuery, groupModel)
                    }
                    isSuccess = true
                    break
                }
            }
        }

        if (isSuccess) return ResponseData.success(true)

        return sendErrorData(
            appMessages.ACCESS_DENIED,
            statusCode = StatusCodeUtil.FORBIDDEN
        )
    }
}