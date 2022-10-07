package com.postmage.service.user_posts

import com.postmage.mongo_client.MongoInitialize
import com.postmage.util.AppMessages

class UserPostsService(
    private val mongoDB: MongoInitialize,
    private val appMessages: AppMessages
) {
}