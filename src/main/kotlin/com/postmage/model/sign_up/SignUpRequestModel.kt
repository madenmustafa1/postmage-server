package com.postmage.model.sign_up

import com.postmage.enums.AppUserRole
import com.postmage.model.group.GroupInfoModel
import com.postmage.model.profile.user.SingleFollowerDataModel
import com.postmage.util.DateUtil
import kotlinx.serialization.Serializable
import java.util.*
import kotlin.collections.ArrayList

@Serializable
data class SignUpRequestModel(
    val nameSurname: String,
    val mail: String,
    var password: String,
    val phoneNumber: String? = null,
    val gender: Int? = null,
    val profilePhotoUrl: String? = null,
    var userId: String? = null,
    val userRole: Int? = AppUserRole.USER.ordinal,
    val following: ArrayList<SingleFollowerDataModel> = arrayListOf(),
    val followers: ArrayList<SingleFollowerDataModel> = arrayListOf(),
    val groups: ArrayList<GroupInfoModel> = arrayListOf(),
    val creationTime: Long? = DateUtil.getTimeNow()
)
