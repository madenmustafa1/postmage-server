package com.postmage.model.posts.get_posts


import com.postmage.model.posts.update_posts.UserCommentModel
import kotlinx.serialization.Serializable
import kotlin.collections.ArrayList

@Serializable
data class GetUserPostModel(
    var photoName: String? = null,
    var description: String = "",
    var groupId: String = "",
    var creationTime: Long? = null,
    var likeUserId: ArrayList<String>? = null,
    var comment: ArrayList<UserCommentModel>? = null,
    var objectId: String? = null,
    var userId: String? = null
)