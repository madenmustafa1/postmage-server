package com.postmage.repo

import com.postmage.enums.StatusCodeUtil
import com.postmage.extensions.authToDataClass
import com.postmage.extensions.writePhotoToDisk
import com.postmage.model.group.AddUsersToGroupModel
import com.postmage.model.group.CreateGroupRequestModel
import com.postmage.model.group.GroupInfoModel
import com.postmage.service.ResponseData
import com.postmage.service.group.GroupInterface
import com.postmage.service.group.GroupService
import com.postmage.util.AppMessages

class GroupRepository(
    private val groupService: GroupService,
    private val appMessages: AppMessages
): GroupInterface {
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

    override suspend fun addUsersToGroup(userId: String, body: AddUsersToGroupModel): ResponseData<Boolean> {
        if (userId == body.id) return sendErrorData(
            appMessages.WRONG_USER_ID,
            statusCode = StatusCodeUtil.BAD_REQUEST,
        )
        return groupService.addUsersToGroup(userId.authToDataClass()!!.userId, body)
    }
}