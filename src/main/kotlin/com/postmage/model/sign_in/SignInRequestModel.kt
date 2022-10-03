package com.postmage.model.sign_in

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestModel(
    val mail: String?,
    val password: String?,
)

