package com.postmage.service.user_posts

import com.postmage.enums.PostType
import com.postmage.model.group.GroupIdModel
import com.postmage.model.posts.add_posts.AddPostModel
import com.postmage.model.posts.followed_users.PostOfFollowedUsers
import com.postmage.model.posts.get_posts.GetUserPostModel
import com.postmage.model.posts.update_posts.UpdateUserPostModel
import com.postmage.service.ResponseData
import com.postmage.vm.UserPostsVM


interface UserPostsInterface {
    suspend fun addPost(
        userId: String,
        body: AddPostModel,
        addPostType: PostType = PostType.ADD_PERSONAL
    ): ResponseData<Boolean>

    suspend fun getMyPost(userId: String): ResponseData<List<GetUserPostModel>>
    suspend fun getGroupPost(userId: String, body: GroupIdModel): ResponseData<List<GetUserPostModel>>
    suspend fun getPost(userId: String, postId: String?): ResponseData<GetUserPostModel>
    suspend fun updatePost(userId: String, body: UpdateUserPostModel): ResponseData<GetUserPostModel>
    suspend fun postOfFollowedUsers(userId: String, body: PostOfFollowedUsers): ResponseData<List<GetUserPostModel>>

}