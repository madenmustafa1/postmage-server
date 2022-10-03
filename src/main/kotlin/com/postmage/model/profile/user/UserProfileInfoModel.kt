package com.postmage.model.profile.user

import com.postmage.enums.AppUserRole
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileInfoModel(
    var nameSurname: String? = null,
    var mail: String? = null,
    var phoneNumber: String? = null,
    var gender: Int? = null,
    var profilePhotoUrl: String? = null,
    var userId: String? = null,
    var group: ArrayList<String?>? = null,
    var followersSize: Int? = null,
    var followingSize: Int? = null,
    var userRole: Int? = AppUserRole.USER.ordinal
)
