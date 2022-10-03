package com.postmage.model.sign_up

import com.postmage.enums.AppUserRole
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestModel(
    val nameSurname: String,
    val mail: String,
    val password: String,
    val phoneNumber: String? = null,
    val gender: Int? = null,
    val profilePhotoUrl: String? = null,
    val userId: String? = null,
    val userRole: Int? = AppUserRole.USER.ordinal
)
