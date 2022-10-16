package com.postmage.repo

import com.postmage.enums.StatusCodeUtil
import com.postmage.extensions.authToDataClass
import com.postmage.extensions.writePhotoToDisk
import com.postmage.model.posts.add_posts.AddPostModel
import com.postmage.model.posts.followed_users.PostOfFollowedUsers
import com.postmage.model.posts.get_posts.GetUserPostModel
import com.postmage.service.ResponseData
import com.postmage.service.user_posts.UserPostsInterface
import com.postmage.service.user_posts.UserPostsService
import com.postmage.util.AppMessages
import com.postmage.util.Directory
import com.postmage.util.ImageUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class UserPostRepository(
    private val userPostsService: UserPostsService,
    private val appMessages: AppMessages
) : UserPostsInterface {
    override suspend fun addPost(userId: String, body: AddPostModel): ResponseData<Boolean> {
        try {
            if (body.photoBytes == null) return sendErrorData(
                appMessages.PHOTO_CANNOT_BE_EMPTY,
                statusCode = StatusCodeUtil.BAD_REQUEST,
            )

            if (body.photoName == null) return sendErrorData(
                appMessages.PHOTO_NAME_CANNOT_BE_EMPTY,
                statusCode = StatusCodeUtil.BAD_REQUEST,
            )

            val result = body.photoBytes!!.writePhotoToDisk(body.photoName!!)
            if (!result) return sendErrorData(
                appMessages.SERVER_ERROR,
                statusCode = StatusCodeUtil.SERVER_ERROR
            )

            /*
            val photoDir = File(Directory.userDesktopDir?.path, body.photoName!!)

            if (withContext(Dispatchers.IO) {
                    photoDir.createNewFile()
                }) {
                File(Directory.userDesktopDir?.path, body.photoName!!).writeBytes(body.photoBytes!!)
            }
             */

            return userPostsService.addPost(userId.authToDataClass()!!.userId, body)
        } catch (e: Exception) {
            return sendErrorData(
                appMessages.SERVER_ERROR,
                statusCode = StatusCodeUtil.SERVER_ERROR
            )
        }
    }

    override suspend fun getMyPost(userId: String): ResponseData<List<GetUserPostModel>> {
        return userPostsService.getMyPost(userId.authToDataClass()!!.userId)
    }

    override suspend fun postOfFollowedUsers(userId: String, body: PostOfFollowedUsers): ResponseData<List<GetUserPostModel>> {
        if (body.limit > 100) body.limit = 100
        return userPostsService.postOfFollowedUsers(userId.authToDataClass()!!.userId, body)
    }
}