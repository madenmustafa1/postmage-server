package com.postmage.service.user_posts

import com.postmage.model.posts.add_posts.AddPostModel
import com.postmage.service.ResponseData


interface UserPostsInterface {
    suspend fun addPost(userId: String, body: AddPostModel): ResponseData<Boolean>

}