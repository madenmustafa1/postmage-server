package com.postmage.vm

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.postmage.enums.PostType
import com.postmage.enums.StatusCodeUtil
import com.postmage.model.group.GroupIdModel
import com.postmage.model.image.DownloadPhotoRequestModel
import com.postmage.model.posts.add_posts.AddPostModel
import com.postmage.model.posts.followed_users.PostOfFollowedUsers
import com.postmage.model.posts.update_posts.UpdateUserPostModel
import com.postmage.plugins.koin
import com.postmage.repo.UserPostRepository
import com.postmage.util.AppMessages
import com.postmage.util.UuidUtil
import com.postmage.util.sendException
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.io.File

class UserPostsVM(
    private val repository: UserPostRepository,
    private val appMessages: AppMessages
) {

    suspend fun addPost(call: ApplicationCall, addPostType: PostType = PostType.ADD_PERSONAL) {
        try {
            val model = AddPostModel()

            val multipartData = call.receiveMultipart()
            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        when (part.name) {
                            "description" -> model.description = part.value
                            "groupId" -> model.groupId = part.value
                        }
                    }

                    is PartData.FileItem -> {
                        model.photoName = UuidUtil.createUuid().toString() + part.originalFileName as String
                        model.photoBytes = part.streamProvider().readBytes()
                    }

                    else -> {}
                }
            }

            val result = repository.addPost(call.request.headers["Authorization"]!!, model, addPostType)

            result.data?.let {
                call.respond(it)
                call.response.status(HttpStatusCode.OK)
                return
            }

            sendException(
                call = call,
                statusCode = result.message?.statusCode ?: 500,
                errorMessage = result.message?.message ?: ""
            )

        } catch (e: NullPointerException) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.UNAUTHORIZED,
                errorMessage = koin.appMessages.UNAUTHORIZED
            )
        } catch (e: MismatchedInputException) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.BAD_REQUEST,
                errorMessage = koin.appMessages.MODEL_IS_NOT_VALID
            )
        } catch (e: Exception) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.SERVER_ERROR,
                errorMessage = appMessages.SERVER_ERROR
            )
        }
    }

    suspend fun getMyPost(call: ApplicationCall) {
        try {
            val result = repository.getMyPost(call.request.headers["Authorization"]!!)

            result.data?.let {
                call.respond(it)
                call.response.status(HttpStatusCode.OK)
                return
            }

            sendException(
                call = call,
                statusCode = result.message?.statusCode ?: 500,
                errorMessage = result.message?.message ?: ""
            )

        } catch (e: NullPointerException) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.UNAUTHORIZED,
                errorMessage = koin.appMessages.UNAUTHORIZED
            )
        } catch (e: Exception) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.SERVER_ERROR,
                errorMessage = appMessages.SERVER_ERROR
            )
        }
    }

    suspend fun getPost(call: ApplicationCall) {
        try {
            val result = repository.getPost(
                call.request.headers["Authorization"] ?: "",
                call.request.queryParameters["postId"] ?: ""
            )

            result.data?.let {
                call.respond(it)
                call.response.status(HttpStatusCode.OK)
                return
            }

            sendException(
                call = call,
                statusCode = result.message?.statusCode ?: 500,
                errorMessage = result.message?.message ?: ""
            )

        } catch (e: NullPointerException) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.UNAUTHORIZED,
                errorMessage = koin.appMessages.UNAUTHORIZED
            )
        } catch (e: Exception) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.SERVER_ERROR,
                errorMessage = appMessages.SERVER_ERROR
            )
        }
    }

    suspend fun getComments(call: ApplicationCall) {
        try {
            val result = repository.getComments(
                call.request.headers["Authorization"] ?: "",
                call.request.queryParameters["postId"] ?: ""
            )

            result.data?.let {
                call.respond(it)
                call.response.status(HttpStatusCode.OK)
                return
            }

            sendException(
                call = call,
                statusCode = result.message?.statusCode ?: 500,
                errorMessage = result.message?.message ?: ""
            )

        } catch (e: NullPointerException) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.UNAUTHORIZED,
                errorMessage = koin.appMessages.UNAUTHORIZED
            )
        } catch (e: Exception) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.SERVER_ERROR,
                errorMessage = appMessages.SERVER_ERROR
            )
        }
    }

    suspend fun updatePost(call: ApplicationCall) {
        try {
            val body = call.receive<UpdateUserPostModel>()
            val result = repository.updatePost(call.request.headers["Authorization"]!!, body)

            result.data?.let {
                call.respond(it)
                call.response.status(HttpStatusCode.OK)
                return
            }

            sendException(
                call = call,
                statusCode = result.message?.statusCode ?: 500,
                errorMessage = result.message?.message ?: ""
            )

        } catch (e: NullPointerException) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.UNAUTHORIZED,
                errorMessage = koin.appMessages.UNAUTHORIZED
            )
        } catch (e: Exception) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.SERVER_ERROR,
                errorMessage = appMessages.SERVER_ERROR
            )
        }
    }

    suspend fun getGroupPost(call: ApplicationCall) {
        try {
            val body = call.receive<GroupIdModel>()
            val result = repository.getGroupPost(call.request.headers["Authorization"]!!, body)

            result.data?.let {
                call.respond(it)
                call.response.status(HttpStatusCode.OK)
                return
            }

            sendException(
                call = call,
                statusCode = result.message?.statusCode ?: 500,
                errorMessage = result.message?.message ?: ""
            )

        } catch (e: NullPointerException) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.UNAUTHORIZED,
                errorMessage = koin.appMessages.UNAUTHORIZED
            )
        } catch (e: Exception) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.SERVER_ERROR,
                errorMessage = appMessages.SERVER_ERROR
            )
        }
    }

    suspend fun postOfFollowedUsers(call: ApplicationCall) {
        try {
            var body: PostOfFollowedUsers? = null
            kotlin.runCatching { body = call.receiveNullable<PostOfFollowedUsers>() }.getOrNull()
            //var body= call.receiveOrNull<PostOfFollowedUsers>()
            if (body == null) body = PostOfFollowedUsers(limit = 100)
            val result = repository.postOfFollowedUsers(call.request.headers["Authorization"]!!, body!!)

            result.data?.let {
                call.respond(it)
                call.response.status(HttpStatusCode.OK)
                return
            }

            sendException(
                call = call,
                statusCode = result.message?.statusCode ?: 500,
                errorMessage = result.message?.message ?: ""
            )

        } catch (e: NullPointerException) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.UNAUTHORIZED,
                errorMessage = koin.appMessages.UNAUTHORIZED
            )
        } catch (e: Exception) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.SERVER_ERROR,
                errorMessage = appMessages.SERVER_ERROR
            )
        }
    }


}