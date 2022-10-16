package com.postmage.service.group

import com.postmage.model.group.CreateGroupRequestModel
import com.postmage.model.group.GroupInfoModel
import com.postmage.service.ResponseData

interface GroupInterface {

    suspend fun createGroup(userId: String, body: CreateGroupRequestModel): ResponseData<GroupInfoModel>
}