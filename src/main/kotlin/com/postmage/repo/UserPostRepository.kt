package com.postmage.repo

import com.postmage.enums.StatusCodeUtil
import com.postmage.extensions.authToDataClass
import com.postmage.model.posts.add_posts.AddPostModel
import com.postmage.service.ResponseData
import com.postmage.service.user_posts.UserPostsInterface
import com.postmage.service.user_posts.UserPostsService
import com.postmage.util.AppMessages
import com.postmage.util.Directory
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

            val photoDir = File(Directory.userDesktopDir?.path, body.photoName!!)

            if (withContext(Dispatchers.IO) {
                    photoDir.createNewFile()
                }) {
                File(Directory.userDesktopDir?.path, body.photoName!!).writeBytes(body.photoBytes!!)
            }

            return userPostsService.addPost(userId.authToDataClass()!!.userId, body)
        } catch (e: Exception) {
            return sendErrorData(
                appMessages.SERVER_ERROR,
                statusCode = StatusCodeUtil.SERVER_ERROR
            )
        }
    }
}