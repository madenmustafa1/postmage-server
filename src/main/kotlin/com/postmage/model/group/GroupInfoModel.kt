package com.postmage.model.group

import com.postmage.model.profile.user.UserProfileInfoModel
import com.postmage.util.DateUtil
import kotlinx.serialization.Serializable

@Serializable
data class GroupInfoModel(
    val groupId: String? = null,
    val groupName: String,
    var description: String = "",
    val photoName: String,
    val adminIds: ArrayList<String> = arrayListOf(),
    val creationTime: Long? = DateUtil.getTimeNow(),
    val groupMembers: ArrayList<UserProfileInfoModel> = arrayListOf(),
)
