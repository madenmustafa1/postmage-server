package com.postmage.service.user_posts

import com.postmage.model.profile.user.UserProfileInfoModel
import com.postmage.service.ResponseData

interface UserPostsInterface {
    suspend fun addPost(userId: String): ResponseData<UserProfileInfoModel?>

}