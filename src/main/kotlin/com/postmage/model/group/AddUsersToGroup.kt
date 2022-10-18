package com.postmage.model.group

import kotlinx.serialization.Serializable

@Serializable
data class AddUsersToGroupModel(
    val id: String,
    val groupId: String
)
