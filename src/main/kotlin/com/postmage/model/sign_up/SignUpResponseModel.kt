package com.postmage.model.sign_up

data class SignUpResponseModel(
    val token: String?,
    val userId: String?,
    val isSuccess: Boolean
)