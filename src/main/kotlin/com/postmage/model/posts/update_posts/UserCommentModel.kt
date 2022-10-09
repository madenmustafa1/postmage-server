package com.postmage.model.posts.update_posts

import kotlinx.serialization.Serializable

@Serializable
data class UserCommentModel(
    val userId: String,
    val comment: String
)