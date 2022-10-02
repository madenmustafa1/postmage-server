package com.postmage.model.sign_up


data class SignUpRequestModel(
    val nameSurname: String,
    val mail: String,
    val password: String,
    val phoneNumber: String?,
    val gender: Int?,
    val profilePhotoUrl: String?,
    val userId: String?,
    val userRole: Int?
)
