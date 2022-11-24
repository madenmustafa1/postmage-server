package com.postmage.repo

import com.postmage.enums.StatusCodeUtil
import com.postmage.extensions.authToDataClass
import com.postmage.extensions.writePhotoToDisk
import com.postmage.model.group.*
import com.postmage.service.ResponseData
import com.postmage.service.group.GroupInterface
import com.postmage.service.group.GroupService
import com.postmage.util.AppMessages

class GroupRepository(
    private val groupService: GroupService,
    private val appMessages: AppMessages
) : GroupInterface {
    override suspend fun createGroup(userId: String, body: CreateGroupRequestModel): ResponseData<GroupInfoModel> {
        if (body.photoBytes == null) return sendErrorData(
            appMessages.PHOTO_CANNOT_BE_EMPTY,
            statusCode = StatusCodeUtil.BAD_REQUEST,
        )

        if (body.photoName == null) return sendErrorData(
            appMessages.PHOTO_NAME_CANNOT_BE_EMPTY,
            statusCode = StatusCodeUtil.BAD_REQUEST,
        )

        if (body.groupName == null) return sendErrorData(
            appMessages.GROUP_NAME_NOT_BE_NULL,
            statusCode = StatusCodeUtil.BAD_REQUEST,
        )

        val result = body.photoBytes!!.writePhotoToDisk(body.photoName!!)
        if (!result) return sendErrorData(
            appMessages.SERVER_ERROR,
            statusCode = StatusCodeUtil.SERVER_ERROR
        )

        return groupService.createGroup(userId.authToDataClass()!!.userId, body)
    }

    override suspend fun addUsersToGroup(userId: String, body: UsersToGroupModel): ResponseData<Boolean> {
        if (userId == body.id) return sendErrorData(
            appMessages.WRONG_USER_ID,
            statusCode = StatusCodeUtil.BAD_REQUEST,
        )
        return groupService.addUsersToGroup(userId.authToDataClass()!!.userId, body)
    }

    override suspend fun removeUsersToGroup(userId: String, body: UsersToGroupModel): ResponseData<Boolean> {
        if (userId == body.id) return sendErrorData(
            appMessages.WRONG_USER_ID,
            statusCode = StatusCodeUtil.BAD_REQUEST,
        )
        return groupService.removeUsersToGroup(userId.authToDataClass()!!.userId, body)
    }

    override suspend fun addAdminToGroup(userId: String, body: UsersToGroupModel): ResponseData<Boolean> {
        if (userId == body.id) return sendErrorData(
            appMessages.WRONG_USER_ID,
            statusCode = StatusCodeUtil.BAD_REQUEST,
        )
        return groupService.addAdminToGroup(userId.authToDataClass()!!.userId, body)
    }

    override suspend fun getMyGroupList(userId: String): ResponseData<List<GetMyGroupListResponseModel>> {
        return groupService.getMyGroupList(userId.authToDataClass()!!.userId)
    }

    override suspend fun getMyGroupInfo(userId: String, groupId: String): ResponseData<List<GroupUsersModel>> {
        if (userId.isEmpty()) return sendErrorData(
            appMessages.USER_ID_NOT_BE_NULL,
            statusCode = StatusCodeUtil.BAD_REQUEST,
        )
        if (groupId.isEmpty()) return sendErrorData(
            appMessages.GROUP_ID_NOT_BE_NULL,
            statusCode = StatusCodeUtil.BAD_REQUEST,
        )
        return groupService.getMyGroupInfo(userId.authToDataClass()!!.userId, groupId)
    }
}