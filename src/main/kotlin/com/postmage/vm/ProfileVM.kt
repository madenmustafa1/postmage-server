package com.postmage.vm

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.postmage.enums.PostType
import com.postmage.enums.StatusCodeUtil
import com.postmage.model.posts.add_posts.AddPostModel
import com.postmage.model.profile.user.SetFollowersDataModel
import com.postmage.model.profile.user.UpdateProfilePhotoModel
import com.postmage.model.profile.user.UserProfileInfoModel
import com.postmage.plugins.koin
import com.postmage.repo.ProfileRepository
import com.postmage.util.AppMessages
import com.postmage.util.UuidUtil
import com.postmage.util.sendException
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class ProfileVM(
    private val repository: ProfileRepository,
    private val appMessages: AppMessages
) {

    suspend fun getMyProfileInfo(call: ApplicationCall) {
        try {
            val result = repository.getMyProfileInfo(call.request.headers["Authorization"]!!)
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
                errorMessage = koin.appMessages.SERVER_ERROR
            )
        }
    }

    suspend fun putMyProfileInfo(call: ApplicationCall) {
        try {
            val body = call.receive<UserProfileInfoModel>()
            val result = repository.putMyProfileInfo(call.request.headers["Authorization"]!!, body)

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
        } catch (e: CannotTransformContentToTypeException) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.BAD_REQUEST,
                errorMessage = koin.appMessages.MODEL_IS_NOT_VALID
            )
        } catch (e: Exception) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.SERVER_ERROR,
                errorMessage = koin.appMessages.SERVER_ERROR
            )
        }
    }

    suspend fun getMyFollowerData(call: ApplicationCall) {
        try {
            val result = repository.getMyFollowerData(call.request.headers["Authorization"]!!)
            result.data?.let {
                call.respond(it)
                call.response.status(HttpStatusCode.OK)
                return
            }

            sendException(
                call = call,
                statusCode = result.message?.statusCode ?: 500,
                errorMessage = result.message?.message ?: appMessages.SERVER_ERROR
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
                errorMessage = koin.appMessages.SERVER_ERROR
            )
        }
    }

    suspend fun putMyFollowerData(call: ApplicationCall) {
        try {
            val body = call.receive<SetFollowersDataModel>()
            val result = repository.putMyFollowerData(call.request.headers["Authorization"]!!, body)

            result.data?.let {
                call.respond(it)
                call.response.status(HttpStatusCode.OK)
                return
            }
            sendException(
                call = call,
                statusCode = result.message?.statusCode ?: 500,
                errorMessage = result.message?.message ?: appMessages.SERVER_ERROR
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
        } catch (e: CannotTransformContentToTypeException) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.BAD_REQUEST,
                errorMessage = koin.appMessages.MODEL_IS_NOT_VALID
            )
        } catch (e: Exception) {
            sendException(
                call = call,
                statusCode = StatusCodeUtil.SERVER_ERROR,
                errorMessage = koin.appMessages.SERVER_ERROR
            )
        }
    }


    suspend fun putMyProfilePhoto(call: ApplicationCall) {
        try {
            val model = UpdateProfilePhotoModel()

            val multipartData = call.receiveMultipart()
            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        model.photoName = UuidUtil.createUuid().toString() + part.originalFileName as String
                        model.photoBytes = part.streamProvider().readBytes()
                    }

                    else -> {}
                }
            }

            val result = repository.putMyProfilePhoto(call.request.headers["Authorization"]!!, model)
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

}