package com.postmage.service.group

import com.postmage.model.group.*
import com.postmage.service.ResponseData

interface GroupInterface {

    suspend fun createGroup(userId: String, body: CreateGroupRequestModel): ResponseData<GroupInfoModel>
    suspend fun addUsersToGroup(userId: String, body: UsersToGroupModel): ResponseData<Boolean>
    suspend fun removeUsersToGroup(userId: String, body: UsersToGroupModel): ResponseData<Boolean>
    suspend fun addAdminToGroup(userId: String, body: UsersToGroupModel): ResponseData<Boolean>
    suspend fun getMyGroupList(userId: String): ResponseData<List<GetMyGroupListResponseModel>>
    suspend fun getMyGroupInfo(userId: String, groupId: String): ResponseData<List<GroupUsersModel>>
}