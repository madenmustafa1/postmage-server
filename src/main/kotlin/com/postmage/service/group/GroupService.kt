package com.postmage.service.group

import com.mongodb.BasicDBObject
import com.postmage.enums.StatusCodeUtil
import com.postmage.model.group.*
import com.postmage.model.profile.user.GetFollowersDataModel
import com.postmage.mongo_client.MongoInitialize
import com.postmage.repo.sendErrorData
import com.postmage.service.ResponseData
import com.postmage.util.AppMessages
import org.bson.types.ObjectId

class GroupService(
    private val mongoDB: MongoInitialize,
    private val appMessages: AppMessages
) : GroupInterface {
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
                //creationTime = body.creationTime,
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

    override suspend fun addUsersToGroup(userId: String, body: UsersToGroupModel): ResponseData<Boolean> {
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
                        //Add users
                        val model = GroupUsersModel(
                            name = userModel.nameSurname ?: "",
                            id = userModel.userId!!,
                            profileUrl = userModel.profilePhotoUrl ?: ""
                        )

                        //Duplicate <Group> group control
                        groupModel.groupUsers
                            .removeIf { it.id == body.id }
                            .also {
                                groupModel.groupUsers.add(model)
                            }

                        //Set <Group> collection
                        mongoDB.getGroupsCollection.replaceOne(groupQuery, groupModel)

                        //Duplicate <User> group control
                        userModel.groups?.let { _ ->
                            userModel.groups!!
                                .removeIf { it.groupId == body.groupId }
                                .also {
                                    userModel.groups!!.add(groupModel)
                                }
                        }

                        mongoDB.getUserCollection.replaceOne(userQuery, userModel)
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

    override suspend fun removeUsersToGroup(userId: String, body: UsersToGroupModel): ResponseData<Boolean> {
        var isSuccess = false
        //Get <Group> collection
        val groupQuery = BasicDBObject("groupId", body.groupId)
        mongoDB.getGroupsCollection.find(groupQuery).limit(1).forEach { groupModel ->
            //Admin Control
            for (i in groupModel.adminIds) {
                if (i == userId) {
                    //Set <Group> collection
                    groupModel.groupUsers.removeIf { it.id == body.id }
                    groupModel.adminIds.removeIf { it == body.id }
                    mongoDB.getGroupsCollection.replaceOne(groupQuery, groupModel)

                    //Find users <User> collection
                    val userQuery = BasicDBObject("userId", body.id)
                    mongoDB.getUserCollection.find(userQuery).limit(1).forEach { userModel ->
                        //Set <User> group control
                        userModel.groups?.let { _ ->
                            userModel.groups!!.removeIf { it.groupId == body.groupId }
                        }
                        mongoDB.getUserCollection.replaceOne(userQuery, userModel)
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

    override suspend fun addAdminToGroup(userId: String, body: UsersToGroupModel): ResponseData<Boolean> {
        var isSuccess = false
        //Get <Group> collection
        val groupQuery = BasicDBObject("groupId", body.groupId)
        mongoDB.getGroupsCollection.find(groupQuery).limit(1).forEach { groupModel ->
            //Admin Control
            for (i in groupModel.adminIds) {
                if (i == userId) {
                    //Set <Group> collection
                    groupModel
                        .adminIds.removeIf { it == body.id }
                        .also { groupModel.adminIds.add(body.id) }

                    mongoDB.getGroupsCollection.replaceOne(groupQuery, groupModel)

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

    override suspend fun getMyGroupList(userId: String): ResponseData<List<GetMyGroupListResponseModel>> {
        val collection = mongoDB.getUserCollection
        val query = BasicDBObject("userId", userId)

        val groupList = arrayListOf<GetMyGroupListResponseModel>()
        //Get <User> collection
        collection.find(query).limit(1).forEach {
            it.groups?.forEach { group ->
                val groupUsersId = arrayListOf<String>()
                for (i in group.groupUsers) groupUsersId.add(i.id)

                groupList.add(
                    GetMyGroupListResponseModel(
                        groupName = group.groupName,
                        photoName = group.photoName,
                        groupUsersId = groupUsersId,
                        isAdmin = group.adminIds.contains(userId),
                        totalUser = group.groupUsers.size
                    )
                )
            }
        }

        return ResponseData.success(groupList)
    }
}