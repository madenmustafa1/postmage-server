package com.postmage.model.password

data class ChangePasswordModel(
    val mail: String,
    val currentPassword: String?,
    val reqPassword: String?,
    val key: String?
)
