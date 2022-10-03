package com.postmage.model.password

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordModel(
    val mail: String,
    val currentPassword: String?,
    val reqPassword: String?,
    val key: String?
)
