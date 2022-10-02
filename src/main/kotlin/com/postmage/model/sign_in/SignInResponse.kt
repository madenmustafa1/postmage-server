package com.postmage.model.sign_in

data class SignInResponseModel(
    val token: String?,
    val userId: String?,
    val isSuccess: Boolean
)